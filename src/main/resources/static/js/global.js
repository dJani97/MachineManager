
function redirectTo(url) {
	if (location != url) {

		location = url;
	}
	console.log(location)
}


function docKeyUpHandler(e) {
	if (e.altKey && e.keyCode == 65) {
		redirectTo("/machine/new");
	}
	else if (e.altKey && e.keyCode == 72) {
		redirectTo("/home");
	}
}

document.addEventListener('keyup', docKeyUpHandler, false);