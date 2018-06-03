function responseToParagraph(api_url, paragraph_id) {
  $(paragraph_id).text("");
  $(paragraph_id).after('<i class="loader"></i>');

  $.get(api_url, response => {
    $(paragraph_id).text(response);
  }).always(msg => {
    $(paragraph_id)
      .siblings("i")
      .remove();
  });
}

function updateListWith(list_id, api_url, response_handler) {
  $(list_id).html("<ul></ul>");
  $(list_id).after('<i class="loader"></i>');
  $.get(api_url, response => response_handler(response)).always(msg => {
    $(list_id)
      .siblings("i")
      .remove();
  });
}

async function updateUserDetails() {
    responseToParagraph("/api/user/count", "#allUsers");
    responseToParagraph("/api/user/countOnline", "#activeUsers");
}

async function updateMachineDetails() {
    responseToParagraph("/api/machine/count", "#allMachines");
    responseToParagraph("/api/machine/countRunning", "#activeMachines");
}

async function updateUserList() {
	const list_id = "#userList";
    const api_url = "/api/user/listOnline";

    updateListWith(list_id, api_url, response => {
      response.forEach(u => {
        $(list_id + " ul").append(
          "<li><a href='/user/view/" + u.id + "'>" + u.lastname + " " + u.firstname + "</a></li>"
        );
      });
      if (response.length == 0) {
        $(list_id + " ul").append("nincs aktív felhasználó");
      }
	});
}

async function updateMachineList() {
    const list_id = "#machineList";
    const api_url = "/api/machine/listRunning";

    updateListWith(list_id, api_url, response => {
      response.forEach(m => {
        $(list_id + " ul").append(
			"<li><a href='/machine/view/" + m.id + "'>" + m.name + "</a></li>"
		);
      });
      if (response.length == 0) {
        $(list_id + " ul").append("nincs futó gép");
      }
    });
}

async function updateDashboard() {
    updateUserDetails();
    updateMachineDetails();
    updateUserList();
    updateMachineList();
}

window.onload = function() {
  const salt = 2000;
  const refresh_time = 60000 + Math.floor(Math.random() * salt);;
  $.ajaxSetup({ timeout: refresh_time - 1000 });
  updateDashboard();
  setInterval(updateDashboard, refresh_time);
};
