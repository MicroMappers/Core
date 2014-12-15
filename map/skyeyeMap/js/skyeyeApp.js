$(function()
{

    var osmUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
    osmAttrib='<a href="http://openstreetmap.org">OpenStreetMap</a> contributors',
    osm = new L.TileLayer(osmUrl, {attribution: osmAttrib}),
    map = new L.Map('map', {layers: [osm], center: new L.LatLng(10.7792375, 125.0045985), zoom: 14 });
    map.attributionControl.setPrefix('Social media is not necessarily representative or verified. Please keep this in mind when interpreting the crisis map.');


    var requestURL = 'http://gis.micromappers.org/AIDRTrainerAPI/rest/skyeye/jsonp/reports/MM_RemoteSenseClicker';
                        $.ajax({
                                type: 'GET',
                                url: requestURL,
                                dataType: 'jsonp',
                                success: renderList,
                                error: FailedRenderList,
                                jsonp: false,
                                jsonpCallback: "jsonp"
                            });

    function removeSmallMapLayer(){
        $("#map_1").remove();
    }

    function renderMap(e){
        var appObject = e.target.options.info;

        var divIndex = 1;
        var map_div = $("<div/>", {id:"map_task_" + divIndex, 'class': 'span4', 'style':'margin-left:0px'});
        var map_canvas = $("<div/>", {id: "map_" + divIndex, 'class': 'map_canvas'});
        map_canvas.css("width", "740px");
        map_canvas.css("height", "740px");
        map_div.append(map_canvas);
        $("#smallMapContainer").prepend(map_div);

        var currentGeoBoundsArray = $.parseJSON(appObject.bounds);

        var southWest1 = L.latLng(currentGeoBoundsArray[3],currentGeoBoundsArray[2]),
            northEast1 = L.latLng(currentGeoBoundsArray[1],currentGeoBoundsArray[0]),
            bounds1 = L.latLngBounds(southWest1, northEast1);

        var centerPoint1 = bounds1.getCenter();



        var selectedMap = L.map("map_" + divIndex,{maxZoom:32, minZoom:14}).setView([centerPoint1.lat, centerPoint1.lng], 22);

        //console.log("image url : " + appObject.imgurl);

        var imageBounds = [[currentGeoBoundsArray[3], currentGeoBoundsArray[2]], [currentGeoBoundsArray[1], currentGeoBoundsArray[0]]];
        L.imageOverlay(appObject.imgurl, imageBounds).addTo(selectedMap);

        L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(selectedMap);

        selectedMap.setMaxBounds(imageBounds);
        var obj = appObject.geo;
        for(var a=0; a < obj.length; a++){
            var o1 = obj[a].geo;
            for(var i=0; i < o1.length; i++){
                console.log(o1[i]);
                console.log(o1[i].layer)
                var geoOps = o1[i].layer;
                var layerType = o1[i].layerType;
                L.geoJson(geoOps,{
                    onEachFeature:function(feature, layer) {
                       // console.log("feature" + feature);
                     //   var rect = L.rectangle(layer.getBounds(), {color: 'red', weight: 1}).addTo(map);
                    },
                    style: function(layer) {
                        switch (layerType) {
                            case 'polyline': return {color:"#030ff9"};
                            case 'polyline2': return {color:"#f90331"};
                            default:return {color:"#030ff9"};
                        }
                    }
                }).addTo(selectedMap);
            }
        }


    }




    function renderList(data) {
        var dataCount = 0;
        $.each(data, function(i, field){
              dataCount++;
              geoDatColection.push( field );
              mapDataCollection = geoDatColection;


         });

        if(dataCount > 0){
           // displayAllRow(data);
            populateMakers();
        }
    }


    function getIconColor(answerString){
        answerString = answerString.trim();
        if(answerString === 'severe'){
            return 'red';
        }

        if(answerString === 'mild'){
            return 'orange';
        }

    }

    function layerInfo(layer, vURL, layerID)
    {
        this.layer=layer;
        this.url = vURL;
    }

    function getSelectedLayerURL(e){
        for( var i in geoLayerCollection){
            var item = geoLayerCollection[i];

            if(item.layer== e.target){
                return item.url;
            }
        }
    }

    function populateMakers(){

        for( var i in mapDataCollection){
            //$.parseJSON(geoBounds);
            var item = $.parseJSON(mapDataCollection[i]);

            var centerPoint = getCenterPoint(item);

            var aMarker =  L.marker([centerPoint.lat, centerPoint.lng],
                {icon: L.AwesomeMarkers.icon({icon: 'tags', prefix: 'fa', markerColor: 'orange'}), info: item }).addTo(map);

            var theMarker = new layerInfo(aMarker, item.imgurl);

            geoLayerCollection.push( theMarker);

            aMarker.on("click", function (e){
                        removeSmallMapLayer();
                        renderMap(e);
                        window.location.href='#uavOpenModal';

                    });

        }
    }


    function getCenterPoint(aJsonObejct){
        var geoBounds = aJsonObejct.bounds;
        geoBoundsArray = $.parseJSON(geoBounds);

        var southWest = L.latLng(geoBoundsArray[3],geoBoundsArray[2]),
            northEast = L.latLng(geoBoundsArray[1],geoBoundsArray[0]),
            bounds = L.latLngBounds(southWest, northEast);

        var centerPoint = bounds.getCenter();

        return centerPoint;
    }

    function FailedRenderList() {
        //console.log("failed");
    }



});



