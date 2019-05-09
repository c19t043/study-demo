const privilege = {
    template: `
	<div class="panel panel-default col-lg-12">
			<div class="panel-body">
				<div class="row">
					<router-link :to="{name:'privilege_add'}" class="btn btn-primary">添加权限</router-link>
				</div>
				<div class="row">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th>id</th>
								<th>url</th>
								<th>operate</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>/account/add</td>
								<td>add</td>
								<td>
									<a href="" class="btn btn-default">编辑</a>
									<a href="" class="btn btn-default">删除</a>
								</td>
							</tr>
							<tr>
								<td>2</td>
								<td>/account/edit</td>
								<td>edit</td>
								<td>
									<a href="" class="btn btn-default">编辑</a>
									<a href="" class="btn btn-default">删除</a>
								</td>
							</tr>
							<tr>
								<td>3</td>
								<td>/account/query</td>
								<td>query</td>
								<td>
									<a href="" class="btn btn-default">编辑</a>
									<a href="" class="btn btn-default">删除</a>
								</td>
							</tr>
							<tr>
								<td>4</td>
								<td>/account/delete</td>
								<td>delete</td>
								<td>
									<a href="" class="btn btn-default">编辑</a>
									<a href="" class="btn btn-default">删除</a>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</body>
	`
}
