package gov.ca.cwds.rest.util;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.hwpf.usermodel.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;


/**
 * @author Intake Team 4
 */
public class DocUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocUtils.class);

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
      LOGGER.warn("ERROR PROCESSING TEMPLATE: {}",e);
      return template;
    }
  }


}
