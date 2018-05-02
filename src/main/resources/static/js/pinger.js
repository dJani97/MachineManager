function ajax_ping(ip, callback) {
	$.get("/api/ping/" + ip, response => {
		if (response == true) {
			callback('responded')			
		} else {
			callback('timeout')
		}
	});
};

function updateAllIcons() {
	const pingables = $('.pingable')

	pingables.each(function(index) {
		if($(this).hasClass('timeout')) {
			$(this).removeClass('timeout')
			$(this).addClass('loader')
		}
				
	    const address = $(this).attr('data-address')
	    
	    new ajax_ping(address, (response, e) => {
	        console.log(response)
	        
	        $(this).removeClass('loader')
	        if(response === 'responded') {
	            $(this).addClass('responded')
	        } else {
	            $(this).addClass('timeout')
	        }
	    })
	})
};

window.onload = function() {
	updateAllIcons();
	setInterval(updateAllIcons, 30000);
};
