const role = {
    template: `
	 <div class="panel panel-default col-lg-12">
			<div class="panel-body">
				<div class="row">
					<router-link :to="{name:'/role_add'}" class="btn btn-primary">添加角色</router-link>
				</div>
				<div class="row">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th>id</th>
								<th>name</th>
								<th>code</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>管理员</td>
								<td>admin</td>
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
	`
}
