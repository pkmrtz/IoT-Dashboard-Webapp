var selectedZeitspanne = 60;
var selectedDeviceID, selectedDeviceName;
setDevice();

async function getNameForID(id) {
    try {
        const cleanedID = id.replace(/"/g, ''); 
        const response = await fetch(`http://localhost:8081/device/${cleanedID}/name`);
        return response.text();
    } catch (error) {
        console.error(error);
        throw error;
    }
}

async function getNewestDevice() {
    try {
        const response = await fetch("http://localhost:8081/device/newestID");
        return response.text();
    } catch (error) {
        console.error(error);
        throw error;
    }
}

function reloadPage() {
    return true;
}

async function submitForm(event) {
    event.preventDefault();

    var name = document.getElementById('name').value;

    var requestOptions = {
        method: 'POST',
        redirect: 'follow'
      };

    fetch("http://localhost:8081/device/add?name="+name, requestOptions) 
        .then(response => {
            if (!response.ok) {
                throw new Error('Fehler bei der Anfrage');
            }
            console.log('Erfolgreich gesendet!');
            location.reload();
        })
        .catch(error => {
            console.error('Fehler:', error);
        });
}

function deleteDevice(id) {
    var requestOptions = {
        method: 'DELETE',
        redirect: 'follow'
      };

    fetch("http://localhost:8081/device/delete/"+id, requestOptions) 
    .then(response => {
        if (!response.ok) {
            throw new Error('Fehler bei der Anfrage');
        }
        console.log('Erfolgreich gesendet!');
        location.reload();
    })
    .catch(error => {
        console.error('Fehler:', error);
    });  
}

function setZeitspanne(minutes) {
    selectedZeitspanne = minutes;
    console.log('Zeitspanne ausgewählt:', selectedZeitspanne, 'Minute(n)');
}

async function setDevice(id) {
    if (!id) {
        id = await getNewestDevice();
        id = id.replace(/"/g, ''); 
    } 
    selectedDeviceID = id;
    selectedDeviceName = await getNameForID(selectedDeviceID);
    document.getElementById("deviceName").innerHTML = selectedDeviceName;
    console.log('Gerät ausgewählt:', selectedDeviceID);
    refresher();
}

async function getTemperature() {
    try {
        const response = await fetch("http://localhost:8081/telemetry/temp?minutes=" + selectedZeitspanne + "&device_id=" + selectedDeviceID);
        const data = await response.json();

        let values, timestamps, tooltips = [];

        timestamps = data.map(entry => {
            var dateString = String(entry[1])
            var date = new Date(dateString)
            var day = date.getDate()
            var month = date.toLocaleString('default', { month: 'short' });
            var hours = date.getHours();
            var minutes = "0" + date.getMinutes();
            var seconds = "0" + date.getSeconds();
            var formattedTime = day + ". " +month+", "+hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
            return formattedTime;
        });

        tooltips = data.map(entry => {
            var dateString = String(entry[1])
            var date = new Date(dateString)
            var day = date.getDate()
            var month = date.toLocaleString('default', { month: 'short' });
            var hours = date.getHours();
            var minutes = "0" + date.getMinutes();
            var seconds = "0" + date.getSeconds();
            var formattedTime = day + ". " +month+", "+hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
            return formattedTime;
        });

        values = data.map(entry => entry[0]);
        

        return [timestamps, values];
    } catch (error) {
        console.error(error);
        throw error;
    }
}

async function getHumidity() {
    try {
        const response = await fetch("http://localhost:8081/telemetry/humid?minutes=" + selectedZeitspanne + "&device_id=" + selectedDeviceID);
        const data = await response.json();

        let values, timestamps, tooltips = [];

        timestamps = data.map(entry => {
            var dateString = String(entry[1])
            var date = new Date(dateString)
            var day = date.getDate()
            var month = date.toLocaleString('default', { month: 'short' });
            var hours = date.getHours();
            var minutes = "0" + date.getMinutes();
            var seconds = "0" + date.getSeconds();
            var formattedTime = day + ". " +month+", "+hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
            return formattedTime;
        });

        tooltips = data.map(entry => {
            var dateString = String(entry[1])
            var date = new Date(dateString)
            var day = date.getDate()
            var month = date.toLocaleString('default', { month: 'short' });
            var hours = date.getHours();
            var minutes = "0" + date.getMinutes();
            var seconds = "0" + date.getSeconds();
            var formattedTime = day + ". " +month+", "+hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
            return formattedTime;
        });

        values = data.map(entry => entry[0]);
        

        return [timestamps, values];
    } catch (error) {
        console.error(error);
        throw error;
    }
}

async function latestTemperature() {
    try {
        const response = await fetch("http://localhost:8081/telemetry/latest/temp?device_id=" + selectedDeviceID);
        const data = await response.text();
        return data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

async function latestHumidity() {
    try {
        const response = await fetch("http://localhost:8081/telemetry/latest/humid?device_id=" + selectedDeviceID);
        const data = await response.text();
        return data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

