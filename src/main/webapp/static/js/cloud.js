
// Cloud Float...
    var $main = $cloud = mainwidth = null;
    var offset1 = 550;
	var offset2 = 0;
	var offset3 = 200;
	
	var offsetbg = 0;
    
    $(function () {
        $main = $("#mainBody");
		$body = $("body");
        $cloud1 = $("#cloud1");
		$cloud2 = $("#cloud2");
        mainwidth = $main.outerWidth();
    });

    /// 飘动
    setInterval(function flutter() {
        if (offset1 >= mainwidth) {
            offset1 = -580;
        }

        if (offset2 >= mainwidth) {
			 offset2 = -580;
        }
        
        offset1 += 0.9;
		offset2 += 0.5;
        $cloud1.css("background-position", offset1 + "px 60px");
        $cloud2.css("background-position", offset2 + "px 360px");
    }, 100);
	
	
//	setInterval(function bg() {
//        if (offsetbg >= mainwidth) {
//            offsetbg =  -580;
//        }
//
//        offsetbg += 0.9;
//        $body.css("background-position", -offsetbg + "px 0")
//    }, 90 );
	