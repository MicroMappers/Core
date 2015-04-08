package aerial;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 4/2/15
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class MBTileProcessor {
    public static void main(String[] args)  {
        try {
            File dir = new File("/Users/jlucas/Documents/DevTest/mbutil/vanatu/20");
            JSONArray jsonArray = new JSONArray();

            String[] dirList = dir.list();
            if (dirList !=null) {
                for (int i=0; i<dirList.length; i++) {
                    File xFolder = new File(dirList[i]);
                    if(xFolder.getName().indexOf(".DS_Store") == -1){
                        System.out.println("X: " + xFolder.getName());
                        String xPoint = xFolder.getName();
                        String yPath = "/Users/jlucas/Documents/DevTest/mbutil/vanatu/20" + File.separator + xFolder.getName();

                        String[] yList = new File(yPath).list();

                        for (int j=0; j<yList.length; j++) {
                            File y = new File(yList[j]);
                            System.out.println("y: " + y.getName());
                            if(y.getName().indexOf(".DS_Store") == -1){
                                String yPoint = y.getName().replace(".png","");

                                String url = generateStaticMapAPIUrl(tile2lat(yPoint), tile2long(xPoint), 20);
                                if(!isValidURL(url)) {
                                    url = generateFilePath(xPoint, yPoint);
                                }

                                if(!url.isEmpty()){
                                    JSONObject obj = new JSONObject();

                                    obj.put("lat", tile2lat(yPoint));
                                    obj.put("lng", tile2long(xPoint));
                                    obj.put("url", url);
                                    jsonArray.add(obj) ;
                                }
                            }

                        }
                    }

                }
                System.out.println(jsonArray.toJSONString());

            }


        } catch (Exception e) {
            // handle exception
        }

    }


    /**
     public static void main(String[] args){
     // TileLatLonBounds("1014552", "576820", 20);
     double x = tile2lat("1014552");
     double y = tile2long("576820");

     }
     **/

    public static double tile2long(String x1) {
        int x = Integer.valueOf(x1) ;
        int z = 20;
        return (x/Math.pow(2,z)*360-180);

    }

    public static double tile2lat(String y1) {
        int z = 20;
        int y = Integer.valueOf(y1) ;
        double n=Math.PI-2*Math.PI*y/Math.pow(2,z);
        return (180/Math.PI*Math.atan(0.5*(Math.exp(n)-Math.exp(-n))));

    }

    public static double getResolution(int zoom){
        double initialResolution = 2 * Math.PI * 6378137 / 256;
        return initialResolution / (Math.pow(2,zoom)) ;
    }


    public static double[] PixelsToMeters(int px, int py, int zoom){
        double originShift = 2 * Math.PI* 6378137 / 2.0;
        double res = getResolution( zoom ) ;
        double mx = px * res - originShift;
        double my = py * res - originShift;

        double[] mXY =   {mx, my};

        return mXY;
    }


    public static double[] MeterToLatLon(double mx, double my){
        double originShift = 2 * Math.PI* 6378137 / 2.0;
        double lon = (mx / originShift) * 180.0 ;
        double lat = (my /originShift) * 180.0  ;

        lat = 180 / Math.PI * (2 * Math.atan( Math.exp( lat * Math.PI / 180.0)) - Math.PI / 2.0)  ;
        double[] latLon = {lat, lon};

        return latLon;
    }

    public static void TileLatLonBounds( String strx, String stry, int zoom) {

        int tx = Integer.parseInt(strx);
        int ty= Integer.parseInt(stry);
        int tileSize = 256;
        double[] minXY = PixelsToMeters( tx*tileSize, ty*tileSize, zoom ) ;
        double[] maxXY = PixelsToMeters( (tx+1)*tileSize, (ty+1)*tileSize, zoom ) ;

        double[] bounds = {minXY[0],minXY[1], maxXY[0], maxXY[1]};

        double[] minLatLng = MeterToLatLon(bounds[0], bounds[1]) ;
        double[] maxLatLng = MeterToLatLon(bounds[2], bounds[3])  ;


        System.out.println(minLatLng[0] + " - "+ minLatLng[1]);
        System.out.println(maxLatLng[0] + " - "+ maxLatLng[1]);
    }

    private static String generateStaticMapAPIUrl(double lat, double lng, int zoom){
        //http://api.tiles.mapbox.com/v4/uaviators.fjb3uy6e/168.31878662109375,-17.74345487051767,19/600x600.png?access_token=pk.eyJ1IjoidWF2aWF0b3JzIiwiYSI6IlpqZEx2UzgifQ.o6vACHfsO6CTk2yluUZwUA
        return "http://api.tiles.mapbox.com/v4/uaviators.fjb3uy6e/" + lng + "," + lat + ",20/800x800.png?access_token=pk.eyJ1IjoidWF2aWF0b3JzIiwiYSI6IlpqZEx2UzgifQ.o6vACHfsO6CTk2yluUZwUA";

    }
    private static String generateFilePath(String x, String y){
        //console.log('https://a.tiles.mapbox.com/v3/bobbysud.57bymn29/19/'+ coord.x + '/' + coord.y + '.png');
        //console.log('https://b.tiles.mapbox.com/v3/bobbysud.57bymn29/19/'+ coord.x + '/' + coord.y + '.png');

        String aTile = "https://a.tiles.mapbox.com/v4/uaviators.fjb3uy6e/20/" + x + "/" + y + "@2x.png?access_token=pk.eyJ1IjoidWF2aWF0b3JzIiwiYSI6IlpqZEx2UzgifQ.o6vACHfsO6CTk2yluUZwUA";
        if(isValidURL(aTile)) {
            return aTile;
        }
        //https://a.tiles.mapbox.com/v4/uaviators.fjb3uy6e/19/507277/288407@2x.png?access_token=pk.eyJ1IjoidWF2aWF0b3JzIiwiYSI6IlpqZEx2UzgifQ.o6vACHfsO6CTk2yluUZwUA
        String bTile = "https://b.tiles.mapbox.com/v4/uaviators.fjb3uy6e/20" + x + "/" + y + "@2x.png?access_token=pk.eyJ1IjoidWF2aWF0b3JzIiwiYSI6IlpqZEx2UzgifQ.o6vACHfsO6CTk2yluUZwUA";

        if(isValidURL(bTile)) {
            return bTile;
        }

        return "";
    }

    public static boolean isValidURL(String url) {
        HttpURLConnection con = null;
        int responseCode = -1;
        boolean isValid = false;
        try {
            URL connectionURL = new URL(url);
            con = (HttpURLConnection) connectionURL.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            responseCode = con.getResponseCode();
            if(responseCode == 200 || responseCode == 202 || responseCode == 204){
                isValid = true;
            }


        }catch (Exception ex) {
            System.out.println("ex Code sendGet: " + ex + " : sendGet url = " + url);
            isValid = false;
            return isValid;
        }

        return isValid;
    }


}
