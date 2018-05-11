
function redirectTo(url) {
	if (location != url) {
		location = url;
	}
	console.log(location)
}


function keyUpHandler(e) {
	if (e.altKey && e.keyCode == 65) {
		redirectTo("/machine/new");
	}
	else if (e.altKey && e.keyCode == 72) {
		redirectTo("/home");
	}
	else if (e.altKey && e.keyCode == 76) {
		redirectTo("/machine/list");
	}
}

document.addEventListener('keyup', keyUpHandler, false);