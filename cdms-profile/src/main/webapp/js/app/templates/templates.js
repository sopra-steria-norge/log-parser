var templates = templates || {};

(function () {

	templates.graph = function(graphOf, maxYValue) {
		return ''+
		'<div class="span4">'+
		'	<h6>'+app.collections.procedureCollection.getProcedureName(graphOf)+'</h6>' +
		'	<div class="graph" data-graphOf="'+graphOf+'" data-maxYValue="'+maxYValue+'">' +
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
	'					<li id="last3h"><a href="#/last3h">Last 3 h</a></li>'+
	'					<li id="last24h"><a href="#/last24h">Last 24 h</a></li>'+
	'					<li id="last72h"><a href="#/last72h">Last 72 h</a></li>'+
	'					<li id="last1w"><a href="#/last1w">Last week</a></li>'+
	'					<li id="last2w"><a href="#/last2w">Last two weeks</a></li>'+
	'					<li id="overview"><a href="#/overview">Overview</a></li>'+
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
			temp += templates.graph(graphOf, null);
		})
		temp += '' +
		'</div>'+
		'<div class="row">'+
			templates.percentileTable+
		'</div>';
		return temp;
	}

	templates.overviewPage = function() {
		temp = '';
		var i = 0;
		temp += '<div class="row">';
		app.collections.procedureCollection.each(function (pro) {
			if (i % 3 === 0) {
				temp += '</div><hr/>';
				temp += '<div class="row">';
			};
			temp += templates.graph(pro.id, 3000);
			i++;
		});
		temp += '</div>';
		return temp;
	}


})(templates);