package image;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 12/7/14
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class MicroMappersInput {

        private String UserName;
        private String Tweet;
        private String TimeStamp;
        private String Location;
        private String Latitude;
        private String Longitude;
        private String ImageLink;
        private String TweetID;

        public MicroMappersInput(){

        }


        public MicroMappersInput(String UserName,String Tweet, String TimeStamp, String Location, String Latitude, String Longitude,String ImageLink, String TweetID){
            this.UserName = UserName;
            this.Tweet = Tweet;
            this.TimeStamp = TimeStamp;
            this.Location = Location;
            this.Latitude = Latitude;
            this.Longitude = Longitude;
            this.ImageLink = ImageLink;
            this.TweetID = TweetID;
        }


}



