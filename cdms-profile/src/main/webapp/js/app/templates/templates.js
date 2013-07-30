var templates = templates || {};

(function () {

	templates.graph = function(graphOf) {
		return ''+
		'<div class="span4">'+
		'	<div class="graph" data-graphOf="'+graphOf+'">' +
		'	</div>'+
		'</div>';
	}

	templates.navbar = '' +
	'<div class="navbar navbar-fixed-top">'+
	'	<div class="navbar-inner">'+
	'		<div class="row-fluid">'+
	'			<div class="offset1 span10">'+
	'				<a class="brand" href="#">CDMS-Profile</a>'+
	'				<ul class="nav">'+
	'					<li id="last24h"><a href="#/last24h">Last 24 h</a></li>'+
	'					<li id="last72h"><a href="#/last72h">Last 72 h</a></li>'+
	'					<li id="last1w"><a href="#/last1w">Last week</a></li>'+
	'					<li id="last2w"><a href="#/last2w">Last two weeks</a></li>'+
	'				</ul>'+
	'			</div>'+
	'		</div>'+
	'	</div>'+
	'</div>';

	templates.percentileTable = '' +
		'<table class="table table-hover table-bordered">'+
		'	<thead id="tablehead">' +
		'	</thead>' +
		'	<tbody id="tablebody">' +
		'	</tbody>'+
		'</table>';

	templates.defaultPage = function(){
		temp = '' +
		'<div class="row">';
		_.each(app.graphOfs, function (graphOf) {
			temp += templates.graph(graphOf);
		})
		temp += '' +
		'</div>'+
		'<div class="row">'+
			templates.percentileTable+
		'</div>';
		return temp;
	}


})(templates);