package qa.qcri.mm.media.service.impl;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.util.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.qcri.mm.media.service.ImagePublishingService;
import qa.qcri.mm.media.tool.PicasaInterfaceClient;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 3/1/15
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("imagePublishingService")
@Transactional(readOnly = false)
public class ImagePublishingServiceImpl implements ImagePublishingService{


    @Override
    public void publishToPicasa(String albumTitle) throws Exception{

        try {
            PicasawebService picasawebService = new PicasawebService("picasaWebService");
            // to do : add Spring boot for authenication info
            PicasaInterfaceClient picasa = new PicasaInterfaceClient(picasawebService, null,null);

            AlbumEntry myAlbum = new AlbumEntry();

            myAlbum.setTitle(new PlainTextConstruct(albumTitle));
            myAlbum.setDescription(new PlainTextConstruct(albumTitle));

            picasa.insertAlbum(myAlbum) ;
        } catch (IOException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServiceException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch(Exception e){
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
