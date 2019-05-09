const account = {
    template: `
        <div class="panel panel-default col-lg-12">
		<div class="panel-body">
			<div class="row">
				<router-link :to="{name:'account_add'}" class="btn btn-primary">添加账户</router-link>
			</div>
			<div class="row">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>id</th>
							<th>username</th>
							<th>password</th>
							<th>nickname</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(account,index) in accountList">
							<td v-text="account.id"></td>
							<td v-text="account.username"></td>
							<td v-text="account.password"></td>
							<td v-text="account.nickname"></td>
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
