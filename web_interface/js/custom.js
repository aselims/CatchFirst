var map;

// Initializing map

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 52.5124522, lng: 13.4309408},
        zoom: 19,
        disableDefaultUI: true
    });

    map.data.setStyle({
        icon: {
            path: google.maps.SymbolPath.CIRCLE,
            scale: 3,
            strokeColor: 'red',
            strokeWeight: 1,
            fillColor: 'red',
            fillOpacity: 0.5
        },
        zIndex: 0
    });

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
