var map;

// Initializing map

function initMap() {

    var customMapType = new google.maps.StyledMapType(
        [{"featureType":"water","elementType":"all","stylers":[{"hue":"#001204"},{"saturation":100},{"lightness":-95},{"visibility":"on"}]},{"featureType":"landscape.man_made","elementType":"all","stylers":[{"hue":"#007F1E"},{"saturation":100},{"lightness":-72},{"visibility":"on"}]},{"featureType":"landscape.natural","elementType":"all","stylers":[{"hue":"#00C72E"},{"saturation":100},{"lightness":-59},{"visibility":"on"}]},{"featureType":"road","elementType":"all","stylers":[{"hue":"#002C0A"},{"saturation":100},{"lightness":-87},{"visibility":"on"}]},{"featureType":"poi","elementType":"all","stylers":[{"hue":"#00A927"},{"saturation":100},{"lightness":-58},{"visibility":"on"}]}],
         {
                  name: 'Custom Style'
         }
    );

    var customMapTypeId = 'custom_style';

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 19,
        center: {lat: 52.5124522, lng: 13.4309408},
        disableDefaultUI: true,
        mapTypeControlOptions: {
            mapTypeIds: [google.maps.MapTypeId.ROADMAP, customMapTypeId]
        }
    });

    map.mapTypes.set(customMapTypeId, customMapType);
    map.setMapTypeId(customMapTypeId);

// Beacons data

/* Beacon 1 */

    var beacon_1 = new google.maps.Marker({
        position: {lat: 52.512597, lng: 13.431352},
        animation: google.maps.Animation.DROP,
        map: map,
        title: 'Bea'
    });

    var beacon_1_details= new google.maps.InfoWindow({
        content: "This is Bea"
    });
    
    beacon_1.addListener('click', function() {
        beacon_1_details.open(map, beacon_1);
    });

/* Beacon 2 */

    var beacon_2 = new google.maps.Marker({
        position: {lat: 52.512244, lng: 13.431205},
        animation: google.maps.Animation.DROP,
        map: map,
        title: 'Connie'
    });

    var beacon_2_details= new google.maps.InfoWindow({
        content: "This is Connie"
    });
    
    beacon_2.addListener('click', function() {
        beacon_2_details.open(map, beacon_2);
    });
}
