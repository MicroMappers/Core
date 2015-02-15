package qa.qcri.mm.trainer.pybossa.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public final class GeoJsonParser {

    private final static String FIELD_COORDINATES = "coordinates";
    private final static String FIELD_FEATURE = "Feature";
    private final static String FIELD_FEATURES = "features";
    private final static String FIELD_FEATURE_COLLECTION = "FeatureCollection";
    private final static String FIELD_GEOMETRY = "geometry";
    private final static String FIELD_GEOMETRIES = "geometries";
    private final static String FIELD_GEOMETRY_COLLECTION = "GeometryCollection";
    private final static String FIELD_PROPERTIES = "properties";
    private final static String FIELD_TYPE = "type";

    private final static String FIELD_PATHS = "paths";
    private final static String FIELD_RINGS = "rings";


    private enum GeometryType {
        //geojson value
        POINT("Point"),
        MULTI_POINT("MultiPoint"),
        LINE_STRING("LineString"),
        MULTI_LINE_STRING("MultiLineString"),
        POLYGON("Polygon"),
        MULTI_POLYGON("MultiPolygon");

        //esri geojson value

        private final String val;

        GeometryType(String val) {
            this.val = val;
        }

        public static GeometryType fromString(String val) {
            for (GeometryType type : GeometryType.values()) {
                if (type.val.equals(val)) {
                    return type;
                }
            }
            return null;
        }
    }

    public JSONArray parseGeoJsonElement(JSONArray jsonArray) {
        try {
            return parseFeatures(jsonArray);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    private JSONArray parseFeatures(JSONArray jsonArray) {
        JSONArray newElements = new JSONArray();
        try {

            for(Object obj : jsonArray ) {
                JSONObject node = (JSONObject)obj;
                JSONArray locations = (JSONArray)node.get("loc");
                for(Object geometryNode: locations){
                    JSONObject geomoetry = (JSONObject)geometryNode;
                    String type = (String)geomoetry.get("type");

                    if(GeometryType.fromString(type).equals(GeometryType.POLYGON)){
                        // do something
                        JSONObject aNewNode = new JSONObject();
                        aNewNode.put(FIELD_RINGS, geomoetry.get(FIELD_COORDINATES)) ;
                        aNewNode.put(FIELD_TYPE, geomoetry.get(FIELD_TYPE));

                        newElements.add(newElements);
                    }
                    if(GeometryType.fromString(type).equals(GeometryType.LINE_STRING)){
                        // do something
                        JSONObject aNewNode = new JSONObject();
                        aNewNode.put(FIELD_PATHS, geomoetry.get(FIELD_COORDINATES)) ;
                        aNewNode.put(FIELD_TYPE, geomoetry.get(FIELD_TYPE));

                        newElements.add(newElements);
                    }

                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return newElements;
    }


}
