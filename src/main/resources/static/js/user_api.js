function deleteUser(id) {
	const api_url = "/api/user/delete/" + id;
	
	if(confirm("FIGYELEM!\nBiztosan törölni szeretnéd ezt a felhasználót?")) {
		$.get(api_url, response => {
			console.log(response);
		}).done(e => {
			location.reload();
		});
	}
}