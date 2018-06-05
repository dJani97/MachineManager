function deleteGroup(id) {
	const api_url = "/api/group/delete/" + id;
	
	if(confirm("FIGYELEM!\n\nA csoport törlésével az összes hozzá tartozó gép is törlésre kerül.\n\nBiztosan törlöd a csoportot?")) {
		$.get(api_url, response => {
			console.log(response);
		}).done(e => {
			location.reload();
		});
	}
}

function createGroup(project_id) {
	const groupName = $('#newGroupForm input').val();
	const api_url = "/api/group/create/" + groupName + "?project_id=" + project_id;
	
	$.get(api_url, response => {
		console.log(response);
	}).done(e => {
		location.reload();
	});
}