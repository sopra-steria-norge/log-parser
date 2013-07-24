var templates = templates || {};

(function () {

	templates.row = '' +
	'<div class="row'+
	'</div>';

	templates.graph = ''+
	'<div class="span4">'+
	'	<div class="graph">' +
	'	</div>'+
	'</div>';

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
	'<table class="table table-striped">'+
	'	<thead>' +
	'		<tr>' +
	'			<th>Name/Percentiles</th>' +
	'			<th>100</th>' +
	'			<th>90</th>' +
	'			<th>80</th>' +
	'			<th>0</th>' +
	'		</tr>' +
	'	</thead>' +
	'	<tbody>' +
	'		<tr data-name="100" class="success">'+
	'			<td></td>' +
	'			<td data-percentile="100">PT0S</td>'+
	'			<td data-percentile="90">PT0S</td>'+
	'			<td data-percentile="80">PT0S</td>'+
	'			<td data-percentile="0">PT0S</td>'+
	'		</tr>'+
	'		<tr data-name="101" class="success">'+
	'			<td></td>'+
	'			<td data-percentile="100">PT0S</td>'+
	'			<td data-percentile="90">PT0S</td>'+
	'			<td data-percentile="80">PT0S</td>'+
	'			<td data-percentile="0">PT0S</td>'+
	'		</tr>'+
	'	</tbody>'+
	'</table>';

	templates.defaultPage= '' +
	'<div class="row">' +
		templates.graph +
		templates.graph +
		templates.graph +
	'</div>'+
	'<div class="row>'+
		templates.percentileTable +
	'</div>';


})(templates);