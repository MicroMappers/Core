$(function()
{
   $('.drawer').slideDrawer({
			showDrawer: false,
			// slideTimeout: true,
			slideSpeed: 600,
			slideTimeoutCount: 3000   });

   $('.scroll-pane').jScrollPane();

    var $select = $("#timerange").multiselect(
        {
            show: ["bounce", 200],
            hide: ["explode", 1000]
        }
    );//apply the plugin
    $select.multiselect('disable');
    //category
    var $select2 = $("#category").multiselect(
        {
            show: ["bounce", 200],
            hide: ["explode", 1000]
        }
    );//apply the plugin
    $select2.multiselect('disable');


    $("#category").multiselect({
            click: function(event, ui){
                    console.log(ui.value + ' ' + (ui.checked ? 'checked' : 'unchecked') );
                if(ui.checked){
                    //var itemIndex = jQuery.inArray(ui.value, selectedCategory);
                    selectedCategory.push(ui.value)  ;
                }
                else{
                    selectedCategory.splice($.inArray(ui.value, selectedCategory),1);
                }
                modifyLayers();
            },
            checkAll: function(){
                    console.log("Check all clicked!");
                    selectedCategory.length = 0;
                    selectedCategory = categorySelectionArr;
                    modifyLayers();
            },
            uncheckAll: function(){
                    console.log("Uncheck all clicked!");
                    selectedCategory.length = 0;
                    modifyLayers();
            },
            optgrouptoggle: function(event, ui){
                    var values = $.map(ui.inputs, function(checkbox){
                            return checkbox.value;
                    }).join(", ");

                    console.log("<strong>Checkboxes " + (ui.checked ? "checked" : "unchecked") + ":</strong> " + values);
            }
    });

    $("#timerange").multiselect({
            click: function(event, ui){
                    console.log(ui.value + ' ' + (ui.checked ? 'checked' : 'unchecked') );
                if(ui.checked){
                    selectedDate.push(ui.value)  ;
                }
                else{
                    // remove
                    selectedDate.splice($.inArray(ui.value, selectedDate),1);
                }
                modifyLayers();


            },
            checkAll: function(){
                    console.log("Check all clicked!");
                    selectedDate.length = 0;
                    selectedDate = dateSelectionArr;
                    modifyLayers();
            },
            uncheckAll: function(){
                    console.log("Uncheck all clicked!");
                    selectedDate.length = 0;
                    modifyLayers();
            },
            optgrouptoggle: function(event, ui){
                    var values = $.map(ui.inputs, function(checkbox){
                            return checkbox.value;
                    }).join(", ");

                    console.log("<strong>Checkboxes " + (ui.checked ? "checked" : "unchecked") + ":</strong> " + values);
            }
    });

    var tweetList = [];

    var cloudmade = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    });

    var map = L.map('map',{zoomControl: false, zoom: 4}).addLayer(cloudmade);
   // var map = L.map('map', {zoom: 13,minZoom: 5, layers: [cloudmade]});
    map.addControl( L.control.zoom({position: 'topright'}) );
    var markers = new L.MarkerClusterGroup({});

    var autoGeoRefresh = setInterval(
                function ()  // Call out to get the time
                {
                        var requestURL = 'http://scd1.qcri.org:8084/AIDRCrowdsourcingAPI/rest/geo/JSONP/geoMap';

                        if(lastUpdated != null && lastUpdated !='' && typeof  lastUpdated != 'undefined'){
                            requestURL = requestURL + '/qdate/' +   encodeURIComponent(lastUpdated);
                        }

                        $.ajax({
                                type: 'GET',
                                url: requestURL,
                                dataType: 'jsonp',
                                success: renderList,
                                error: FailedRenderList,
                                jsonp: false,
                                jsonpCallback: "jsonp"
                            });


                }, 5000);// end check



    function renderList(data) {
        var infoUpdated = [];
        var dataCount = 0;
        $.each(data, function(i, field){
              dataCount++;
              infoUpdated.push(field.info.updated);

              var tweetCreated = Date.parse(field.info.created).toString('d-MMM-yyyy HH:00 tt');
              var tweetCategory = field.info.answer;

              tweetList.push(field.info);
              geoDatColection.push( field );
              mapDataCollection = geoDatColection;

              var found = false;
              var foundAnswer = false;

            for ( var i in dateSelectionArr ) {
                if(dateSelectionArr[ i ]== tweetCreated){
                    found = true;
                }
              }

              if(!found){
                  dateSelectionArr.push( tweetCreated );
              }

              for ( var i in categorySelectionArr ) {
                if(categorySelectionArr[ i ]== tweetCategory){
                    foundAnswer = true;
                }
              }


              if(!foundAnswer){
                  categorySelectionArr[ categorySelectionArr.length + 1] = tweetCategory;
              }
         });

        if(dataCount > 0){
            if(infoUpdated.length > 0){
                infoUpdated.sort();
                lastUpdated = infoUpdated[infoUpdated.length - 1];
            }

            infoUpdated.length = 0;

            populateMakers();
            populateDateFilter();
            populateCategoryFilter();
           // loadTweetDisplay();
            createDataTable();

            map.addLayer(markers);
            map.fitBounds(markers.getBounds());
        }
    }

    function createDataTable(){
        if(typeof oTable == 'undefined'){
             oTable = $('#example').dataTable( {
                        "sDom": '<"top"ifp>rt<"clear">',
                        "bLengthChange": false,
                        "bFilter": true,
                        "sScrollY": "230px",
                        "bScrollCollapse": true,
                        "bProcessing": true,
                        "bJQueryUI": true,
                        "aaData": tweetList,
                        "sPaginationType": "full_numbers",
                        "aoColumns": [
                            { "mData": "tweet" },
                            { "mData": "answer" },
                            { "mData": "created" } ]
                    } );

            addDataTableRowSelectionEvent();
        }

    }

    function fnGetSelected( oTableLocal )
    {
        return oTableLocal.$('tr.row_selected');
    }
    function loadTweetDisplay(){
       var refreshRate = 5000;
       var refreshRate2 = 3000;
       var autoRefresh = setInterval(
                function ()  // Call out to get the time
                {
                    $('#tweetList').find('li ').first().remove();
                    if(tweetDisplayIndex >= tweetList.length){
                        tweetDisplayIndex = 0;
                    }
                    for(var i=0; i < 1; i++){
                        var info = tweetList[tweetDisplayIndex];

                        var displayTxt = '<p><b>'+info.answer+'</b></p><p>'+info.tweet+'</p>';
                        displayTxt =  displayTxt + '<p>'+ info.created+'   '+info.author+'</p>';
                      //  displayTxt =  displayTxt + '<p><b>'+info.answer+'</b></p>';

                        if(info.url !='' && typeof info.url != 'undefined'){
                             displayTxt =  displayTxt + '<img style="-webkit-user-select: none" src="' +info.url+ '" width="200" height="200">'
                        }
                        $("#tweetList").append($("<li class='ui-widget-content'></li>").html(displayTxt));
                        $("#tweetList").fadeIn( "slow" );
                        tweetDisplayIndex++;
                    }
                }, refreshRate);// end check

        //var autoRefresh2 = setInterval(
          //      function ()  // Call out to get the time
            //    {
              //      $('#tweetList').find('li ').first().remove();

             //   }, refreshRate2);// end check
    }

    function populateDateFilter(){
        dateSelectionArr.sort();

        $("#timerange").multiselect('enable');
        for ( var i in dateSelectionArr ) {
            $("#timerange").append($("<option></option>").val(dateSelectionArr[i]).html(dateSelectionArr[i]));
        }

        $("#timerange").multiselect('refresh');
    }

    function populateCategoryFilter(){
        categorySelectionArr.sort();


        $("#category").multiselect('enable');
        for ( var i in categorySelectionArr ) {
            $("#category").append($("<option></option>").val(categorySelectionArr[i]).html(categorySelectionArr[i]));
        }

        $("#category").multiselect('refresh');
    }

    function populateMakers(){
        for( var i in mapDataCollection){
            var item = mapDataCollection[i];

            if(!checkDuplicateEntry(item.info.tweetID)){
                var layName = L.geoJson(item.features, {
                    onEachFeature: function (features, layer) {
                        layer.bindPopup(item.info.tweet);
                        layer.on("mouseover", function (e){
                            layer.openPopup();
                            displaySelectedLayer(layer);
                        });
                        geoLayerCollection.push( new layerInfo(layer, item.info.tweetID) ) ;
                    }
                });

                markers.addLayer(layName);

            }
        }

    }

    function modifyLayers(){
        markers.clearLayers();
        geoLayerCollection.length = 0;
        if(jQuery.isEmptyObject(selectedCategory) && jQuery.isEmptyObject(selectedDate)){
            displayAllLayers();
        }
        else{
            var searchTweetList = [];
            for( var i in mapDataCollection){
                var item = mapDataCollection[i];

                var itemAnswer = item.info.answer;
                var itemCreated = Date.parse(item.info.created).toString('d-MMM-yyyy HH:00 tt');

                var found = locateSearchFilterMeet(itemAnswer, itemCreated);

                if(!checkDuplicateEntry(item.info.tweetID) && found){
                    var layName = L.geoJson(item.features, {
                        onEachFeature: function (features, layer) {
                            layer.bindPopup(item.info.tweet);
                            layer.on("mouseover", function (e){
                            layer.openPopup();
                            displaySelectedLayer(layer);
                        });
                        geoLayerCollection.push( new layerInfo(layer, item.info.tweetID) ) ;
                        }
                    });
                    searchTweetList.push(item.info);
                    markers.addLayer(layName);
                   // geoLayerCollection.push( new layerInfo(layName, item.info.tweetID) ) ;
                }
            }
            refreshTable(searchTweetList);
            map.addLayer(markers);
            map.fitBounds(markers.getBounds())
        }
    }

    function displayAllLayers(){

        for( var i in mapDataCollection){
            var item = mapDataCollection[i];
            if(!checkDuplicateEntry(item.info.tweetID)){
                var layName = L.geoJson(item.features, {
                    onEachFeature: function (features, layer) {
                        layer.bindPopup(item.info.tweet);
                    }
                });

                markers.addLayer(layName);
                geoLayerCollection.push( new layerInfo(layName, item.info.tweetID) ) ;
            }
        }

        map.addLayer(markers);
        map.fitBounds(markers.getBounds())
    }

    function FailedRenderList() {
        console.log("failed");
    }

    function layerInfo(layer, tweetID)
    {
        this.layer=layer;
        this.tweetID = tweetID;
    }

    function checkDuplicateEntry(id){
        var returnValue = false;

        for( var i in geoLayerCollection){
            var layers = geoLayerCollection[i];

            if(layers.tweetID == id){
                returnValue = true;
            }

        }

        return returnValue;
    }

    function getLayer(id){
        for( var i in geoLayerCollection){
            var item = geoLayerCollection[i];

            if(item.tweetID == id){
                return item;
            }

        }
    }
    function locateSearchFilterMeet(itemAnswer, itemCreated){
        // and conidtion should be enforced
        var found = false;
        //if( && jQuery.isEmptyObject(selectedDate)){
        if(jQuery.isEmptyObject(selectedCategory)){
            found = true;
        }
        else{
            for( var a in selectedCategory){
                       if(selectedCategory[a] == itemAnswer){
                            found = true;
                       }
             }
        }

        if(found){
            found = false;
            if( jQuery.isEmptyObject(selectedDate)){
                found = true;
            }
            else{
                for( var a in selectedDate){
                           if(selectedDate[a] == itemCreated){
                                found = true;
                           }
                }
            }
        }


        return found;

    }

    function refreshTable(thisData)
    {
            oSettings = oTable.fnSettings();
            oTable.fnClearTable(this);
            for (var i=0; i<thisData.length; i++)
            {
              oTable.oApi._fnAddData(oSettings, thisData[i]);
            }

            oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
            oTable.fnDraw();

         //   addDataTableRowSelectionEvent();

    }

    function addDataTableRowSelectionEvent(){
        $('body').delegate('#example tbody tr', "click", function () {
         //   $('#example tbody tr').bind('click', function( e ) {
            if ( $(this).hasClass('row_selected') ) {
                $(this).removeClass('row_selected');
            }
            else {
                oTable.$('tr.row_selected').removeClass('row_selected');
                $(this).addClass('row_selected');
                var data = oTable.fnGetData( this );
                displaySelectedRow(data);
                var thisLayer = getLayer(data.tweetID);
                var e = thisLayer.layer;
               // this.e = e;
                //this.e.openPopup();

                markers.zoomToShowLayer(e, function () {
                                e.openPopup();
                });

            }
        });
    }

    function displaySelectedRow(info){
            $('#tweetList').find('li ').first().remove();

            var displayTxt = '<p><b>'+info.answer+'</b></p><p>'+info.tweet+'</p>';
            displayTxt =  displayTxt + '<p>'+ info.created+'   '+info.author+'</p>';

            if(info.url !='' && typeof info.url != 'undefined'){
                 displayTxt =  displayTxt + '<img style="-webkit-user-select: none" src="' +info.url+ '" width="200" height="200">'
            }
            $("#tweetList").append($("<li class='ui-widget-content'></li>").html(displayTxt));
            $("#tweetList").fadeIn( "slow" );
    }

    function displaySelectedLayer(layer){
        for( var i in geoLayerCollection){
            var item = geoLayerCollection[i];
            if(item.layer == layer){
                for( var i in mapDataCollection){
                    var aItem = mapDataCollection[i];
                    if(aItem.info.tweetID == item.tweetID){
                        displaySelectedRow(aItem.info);
                    }
                }
            }
        }
    }
});

