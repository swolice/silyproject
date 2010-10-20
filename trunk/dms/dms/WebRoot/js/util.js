function CD(a) {
	if (a.startTime == null)
		throw "args:startTime is null";
	if (a.endTime == null)
		throw "args:endTime is null";
	this.startTime = a.startTime.constructor == Date
			? a.startTime.getTime()
			: a.startTime;
	this.endTime = a.endTime.constructor == Date
			? a.endTime.getTime()
			: a.endTime;
	this.callback = a.callback || new Function;
	this.startCount()
}

CD.prototype = {
	startCount : function() {
		var a = this.startTime, c = this.endTime, d = this.callback, g = setInterval(
				function() {
					a += 1E3;
					if (a > c)
						clearInterval(g);
					else {
						var b = (c - a) / 1E3, e = parseInt(b / 3600 / 24), f = parseInt((b - e
								* 24 * 3600)
								/ 3600), h = parseInt((b - e * 24 * 3600 - f
								* 3600)
								/ 60);
						b = parseInt(b - e * 24 * 3600 - f * 3600 - h * 60);
						d(e, f, h, b)
					}
				}, 1E3)
	}
};



function use_tab(arr){
    var dis_arr = [];
    jQuery(arr).each(function(){
        dis_arr.push(this[1])
    });
    jQuery(arr).each(function(){
        var i = this;
        jQuery(i[0]).click(function(){
            jQuery(arr).each(function(){
                jQuery(this[0]).removeClass("on");
                jQuery(this[1]).hide();
            });
            jQuery(i[0]).addClass('on');
            jQuery(i[1]).show();
        })
    })
}
