function responseToParagraph(api_url, paragraph_id) {
  $(paragraph_id).text("");
  $(paragraph_id).after('<i class="loader"></i>');

  $.get(api_url, response => {
    $(paragraph_id).text(response);
    $(paragraph_id)
      .siblings("i:first")
      .remove();
  });
}

async function updateUserDetails() {
  return new Promise(resolve => {
    responseToParagraph("/api/user/count", "#allUsers");
    responseToParagraph("/api/user/countOnline", "#activeUsers");
  });
}

async function updateMachineDetails() {
  return new Promise(resolve => {
    responseToParagraph("/api/machine/count", "#allMachines");
    responseToParagraph("/api/machine/countRunning", "#activeMachines");
  });
}

async function updateUserList() {
  return new Promise(resolve => {
    $("#userList").html("<ul></ul>");
    $("#userList").after('<i class="loader"></i>');

    $.get("/api/user/listOnline", response => {
      response.forEach(u => {
        $("#userList ul").append(
          "<li>" + u.lastname + " " + u.firstname + "</li>"
        );
      });
      $("#userList")
        .siblings("i:first")
        .remove();
      if (response.length == 0) {
        $("#userList ul").append("nincs aktív felhasználó");
      }
    });
  });
}

async function updateMachineList() {
  return new Promise(resolve => {
    $("#machineList").html("<ul></ul>");
    $("#machineList").after('<i class="loader"></i>');

    $.get("/api/machine/listRunning", response => {
      response.forEach(m => {
        $("#machineList ul").append("<li>" + m.name + "</li>");
      });
      $("#machineList")
        .siblings("i:first")
        .remove();
      if (response.length == 0) {
        $("#machineList ul").append("nincs futó gép");
      }
    });
  });
}

async function updateDashboard() {
  await Promise.all([
    updateUserDetails(),
    updateMachineDetails(),
    updateUserList(),
    updateMachineList()
  ]);
}

window.onload = function() {
  updateDashboard();
  setInterval(updateDashboard, 30000);
};
