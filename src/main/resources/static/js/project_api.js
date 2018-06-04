function deleteProject(id) {
	const api_url = "/api/project/delete/" + id;
	
	if(confirm("Biztosan törlöd a projektet?")) {
		$.get(api_url, response => {
			console.log(response);
		}).done(e => {
			location.reload();
		});
	}
}

function createProject() {
	const projectName = $('#newProjectForm input').val();
	const api_url = "/api/project/create/" + projectName;
	
	$.get(api_url, response => {
		console.log(response);
	}).done(e => {
		location.reload();
	});
}