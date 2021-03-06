/*- 
Copyright [2020] Karthik.V

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
function send(name, details, description) {
	var url = '/events/publish';
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			document.body.innerHTML = xhr.responseText;
		}
	}
	var obj = new Object();
	obj.name = name;
	obj.details = details;
	obj.description = description;
	var jsonString = JSON.stringify(obj);
	xhr.open('POST', url, true);
	xhr.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	xhr.send(jsonString);
}
function showClock() {
	var dateObj = new Date(), minutes = formatValue(dateObj.getMinutes()), hours = formatValue(dateObj
			.getHours()), seconds = formatValue(dateObj.getSeconds()), ampm = dateObj
			.getHours() >= 12 ? 'pm' : 'am', months = [ 'Jan', 'Feb', 'Mar',
			'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ], days = [
			'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ];
	document.getElementById('clock').innerHTML = days[dateObj.getDay()] + ' '
			+ months[dateObj.getMonth()] + ' ' + dateObj.getDate() + ' '
			+ dateObj.getFullYear() + ' ' + hours + ':' + minutes + ':'
			+ seconds + ampm;
	setTimeout(showClock, 500);
}
function formatValue(value) {
	if (value < 10) {
		value = '0' + value;
	}
	return value;
}
function closeEvent(eid) {
	var url = '/events/close';
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			document.body.innerHTML = xhr.responseText;
		}
	}
	var obj = new Object();
	obj.eid = eid;
	var jsonString = JSON.stringify(obj);
	xhr.open('PUT', url, true);
	xhr.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	xhr.send(jsonString);
}