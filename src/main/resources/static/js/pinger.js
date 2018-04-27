function ping(ip, callback) {
    
    if (!this.inUse) {
        this.status = 'unchecked';
        this.inUse = true;
        this.callback = callback;
        this.ip = ip;
        var _that = this;
        this.img = new Image();
        this.img.onload = function () {
            _that.inUse = false;
            _that.callback('responded');

        };
        this.img.onerror = function (e) {
            if (_that.inUse) {
                _that.inUse = false;
                _that.callback('responded', e);
            }

        };
        this.start = new Date().getTime();
        this.img.src = "http://" + ip;
        this.timer = setTimeout(function () {
            if (_that.inUse) {
                _that.inUse = false;
                _that.callback('timeout');
            }
        }, 1500);
    }
};

function ajax_ping(ip, callback){
	$.get("/api/ping/" + ip, response => {
		if (response == true) {
			callback('responded')			
		} else {
			callback('timeout')
		}
	});
}

window.onload = function() {
	const pingables = $('.pingable')

	pingables.each(function(index) {
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
