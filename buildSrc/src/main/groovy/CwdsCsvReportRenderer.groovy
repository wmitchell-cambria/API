import com.github.jk1.license.ModuleData;
import com.github.jk1.license.ProjectData;
import com.github.jk1.license.LicenseReportExtension
import com.github.jk1.license.render.CsvReportRenderer;
import com.github.jk1.license.render.LicenseDataCollector;

public class CwdsCsvReportRenderer extends CsvReportRenderer {
    @Override
    public void render(ProjectData data){
        LicenseReportExtension config = data.project.licenseReport
        File output = new File(config.outputDir, filename)
        output.write('')

        if (includeHeaderLine) {
            output << header();
        }

        data.allDependencies.sort().each {
            renderDependency(output, it)
        }
    }

    CwdsCsvReportRenderer(String filename = 'licenses.csv') {
        this.filename = filename
    }

    void renderDependency(File output, ModuleData data) {
        output << line(data)
    }

    private String header(){
        return "${quote('artifact')}$separator${quote('name')}$separator${quote('version')}$separator${quote('moduleUrl')}$separator${quote('moduleLicense')}$separator${quote('moduleLicenseUrl')}$separator${quote('projectUrl')}$separator$nl"
    }

    private String line(ModuleData data){
        def (String moduleUrl, String moduleLicense, String moduleLicenseUrl) = LicenseDataCollector.singleModuleLicenseInfo(data)
        String artifact = "${data.group}:${data.name}:${data.version}"
        return "${quote(artifact)}$separator${quote(data.name)}$separator${quote(data.version)}$separator${quote(moduleUrl)}$separator${quote(moduleLicense)}$separator${quote(moduleLicenseUrl)}$separator${quote(projectUrl(data))}$separator$nl"
    }

    private String projectUrl(ModuleData data){
        Set urls = new HashSet();
        data.poms.each {
            urls << it.projectUrl
        }
        urls.join(" ")
    }

    private String quote(String content) {
        if (content == null || content.isEmpty()) {
            return ''
        }
        content = content.trim()
        content = content.replaceAll(quote, "\\\\$quote")
        "${quote}${content}${quote}"
    }
}