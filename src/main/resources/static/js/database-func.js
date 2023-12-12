var selectedZeitspanne = 60;

function setZeitspanne(minutes) {
    selectedZeitspanne = minutes;
    console.log('Zeitspanne ausgewählt:', selectedZeitspanne, 'Minute(n)');
}

function setDevice(id) {
    selectedDevice = id;
    console.log('Gerät ausgewählt:', selectedDevice);
}

async function getTemperature() {
    try {
        const response = await fetch("http://localhost:8080/telemetry/temp?minutes=" + selectedZeitspanne + "&device_id=" + selectedDevice);
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
        const response = await fetch("http://localhost:8080/telemetry/humid?minutes=" + selectedZeitspanne + "&device_id=" + selectedDevice);
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
        const response = await fetch("http://localhost:8080/telemetry/latest/temp?device_id=" + selectedDevice);
        const data = await response.text();
        return data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

async function latestHumidity() {
    try {
        const response = await fetch("http://localhost:8080/telemetry/latest/humid?device_id=" + selectedDevice);
        const data = await response.text();
        return data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

