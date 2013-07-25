var templates = templates || {};

(function () {



	templates.row = '' +
	'<div class="row'+
	'</div>';

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

	templates.percentileTable = function(){
		
		return '' +
		'<table class="table table-striped">'+
		'	<thead id="tablehead">' +
		'	</thead>' +
		'	<tbody id="tablebody">' +
		'	</tbody>'+
		'</table>';

		/*
		temp = '' +
		'<table class="table table-striped">'+
		'	<thead>' +
		'		<tr>' +
		'			<th>Name/Percentiles</th>';
		for (var i = 0; i < app.percentiles.length; i++) {
			temp += '			<th>'+app.percentiles[i]+'</th>';
		}
		temp += '' +
		'		</tr>' +
		'	</thead>' +
		'	<tbody>';
		console.log(app.procedureMapping)
		for (var i = 0; i < app.procedureMapping.length; i++) {
			temp += '		<tr data-name="'+app.procedureMapping[i].name+'" data-rowOf="'+app.procedureMapping[i].id+'" class="success">'
			temp += '			<td>'+app.procedureMapping[i].name+'</td>';
			for (var j = 0; j < app.percentiles.length; j++) {
				temp += '			<td data-percentile="'+app.percentiles[j]+'">PT0S</td>';
			};
			temp += '		</tr>'
		};
		temp += ''+
		'	</tbody>'+
		'</table>';

		return temp;
		*/
		
	}

	templates.defaultPage = function(){
		return '' +
		'<div class="row">' +
			templates.graph('1') +
			templates.graph('18') +
			templates.graph('1, 18') +
		'</div>'+
		'<div class="row">'+
			templates.percentileTable() +
		'</div>';
	}


})(templates);