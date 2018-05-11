window.setTimeout(function () {

    $(".alert").slideUp(500, function () {
        $(this).remove();
    });
}, 3000);