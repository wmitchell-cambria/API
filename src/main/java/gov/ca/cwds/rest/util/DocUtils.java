package gov.ca.cwds.rest.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.hwpf.usermodel.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Random;


/**
 * @author Intake Team 4
 */
public class DocUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocUtils.class);
  private static final String TEMPLATE_LOCATION = "cms/drmstemplates";

  private DocUtils() {
    //static methods only
  }

  public static byte[] createFromTemplateUseBookmarks(byte[] template, Map<String, String> keyValuePairs){

    try(HWPFDocument document = new HWPFDocument(new ByteArrayInputStream(template))){

      Bookmarks bookmarks = document.getBookmarks();
      for(int i = 0; i < bookmarks.getBookmarksCount(); i++){
        Bookmark bookmark = bookmarks.getBookmark(i);
        String value = keyValuePairs.get(bookmark.getName());
        if( value != null){
          Range range = new Range(bookmark.getStart(), bookmark.getEnd(), document);
          //Bookmar text should contain bookmark name in squere brackets . Problems replacing text in empty bookmarks
          range.replaceText("["+bookmark.getName()+"]", value);
        }
      }

      ByteArrayOutputStream out = new ByteArrayOutputStream(template.length);
      document.write(out);
      return out.toByteArray();

    } catch (Exception e){
      LOGGER.warn("ERROR PROCESSING TEMPLATE: {}", e);
      return template;
    }
  }

  @SuppressFBWarnings("PREDICTABLE_RANDOM") // Random is a temp implementation till we know how
  public static String generateDocHandle(String docId, String docAuth){
    Random random = new Random();
    return CmsKeyIdGenerator.getUIIdentifierFromKey(docId).replace("-","")
            .concat("*")
            .concat(StringUtils.rightPad(docAuth.substring(0, 7), 8))
            .concat(StringUtils.leftPad(String.valueOf(random.nextInt(99999)), 5, "0"));
  }

  public static String loadTemplateBase64(String docName){
    try{
      return DatatypeConverter.printBase64Binary(
              IOUtils.toByteArray(
                  DocUtils.class.getClassLoader().getResourceAsStream(TEMPLATE_LOCATION + "/" + docName)));
    }catch (Exception e){
      LOGGER.error("ERROR LOADING TEMPLATE {} FROM RESOURCES: {}", docName, e);
      return "";
    }
  }

}
