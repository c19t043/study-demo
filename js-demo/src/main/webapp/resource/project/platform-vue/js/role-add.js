const role_add = {
    template: `
	<div class="panel panel-default col-lg-12">
			<div class="panel-body col-lg-6 col-lg-offset-4">
				<form class="form form-horizontal">
					<div class="form-group">
						<label for="name" class="col-lg-2 text-right">角色名</label>
						<div id="col-lg-4">
							<input type="text" id="name" name="name" placeholder="输入角色名">
						</div>
					</div>
					<div class="form-group">
						<label for="code" class="col-lg-2 text-right">角色编码</label>
						<div id="col-lg-4">
							<input type="text" id="code" name="code" placeholder="输入角色编码">
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
