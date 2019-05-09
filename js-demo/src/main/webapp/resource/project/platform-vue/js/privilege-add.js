const privilege_add = {
    template: `
	<div class="panel panel-default col-lg-12">
			<div class="panel-body col-lg-6 col-lg-offset-4">
				<form class="form form-horizontal">
					<div class="form-group">
						<label for="url" class="col-lg-2 text-right">url</label>
						<div id="col-lg-4">
							<input type="text" id="url" name="url" placeholder="输入url">
						</div>
					</div>
					<div class="form-group">
						<label for="operate-code" class="col-lg-2 text-right">operate-code</label>
						<div id="col-lg-4">
							<input type="text" id="operate-code" name="operate-code" placeholder="输入operate-code">
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
