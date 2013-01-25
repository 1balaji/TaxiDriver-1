function getImage(x,y) {
	
	$("#div"+x+y).load("/scripts/getImage?X="+x+"&Y="+y);
	
}

function updateImageField(x,y) {
	//$("#div"+x+y).load("/scripts/?X="+x+"&Y="+y+"&action=rotate");
	console.log("X:"+x);
	
	//var result = $.get("updateField/"+x+"/"+y, , function(data) {
  		//$('#img'+x+y).html(data);
  		//alert('Load was performed.');
	//});
	var strUrl = "updateField/"+x+"/"+y;
	var strReturn = "";
	jQuery.ajax({
    	url: strUrl,
    	success: function(html) {
      		strReturn = html;
    	},
    	async:false
  	});
	console.log(strReturn);
	document.getElementById("img"+x+y).setAttribute("src", "/assets/images/"+strReturn);
	console.log("update the image");

}

function getWhatUWant(url) {
	
	var strReturn = "";
	jQuery.ajax({
    	url: url,
    	success: function(html) {
      		strReturn = html;
    	},
    	async:false
  	});
  	return strReturn;
}

function calculate() {
	var strUrl = "calculate";
	var strReturn = "";
	jQuery.ajax({
    	url: strUrl,
    	success: function(html) {
    		console.log(html);
      		strReturn = html;
    	},
    	async:false
  	});
  	
  	var rDist = $.get("/getDist");
  	console.log(rDist.responseText);
  	var rEarn = $.get("/getDist");
  	var rInfo = $.get("/getInfo");
  	$("#resultdist").html(getWhatUWant("/getDist"));
  	$("#resultearn").html(getWhatUWant("/getEarn"));
  	$("#resultinfo").html(getWhatUWant("/getInfo"));
  		
}

function newRound() {
	document.href="index.html";
	document.location.reload();
}
