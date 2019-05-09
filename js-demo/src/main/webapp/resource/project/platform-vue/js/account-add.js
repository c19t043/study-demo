const account_add = {
    template: `
		<div class="panel panel-default col-lg-12">
			<div class="panel-body col-lg-6 col-lg-offset-4">
				<form class="form-horizontal">
					<div class="form-group">
						<label for="username" class="col-lg-2 text-right">用户名</label>
						<div id="col-lg-4">
							<input type="text" id="username" name="username" placeholder="输入用户名">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-lg-2 text-right">密码</label>
						<div id="col-lg-4">
							<input type="text" id="password" name="password" placeholder="输入密码">
						</div>
					</div>
					<div class="form-group">
						<label for="nickname" class="col-lg-2 text-right">昵称</label>
						<div id="col-lg-4">
							<input type="text" id="nickname" name="nickname" placeholder="输入昵称">
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-4 col-lg-offset-2">
							<button type="button" class="btn btn-primary">提交</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	`
}
