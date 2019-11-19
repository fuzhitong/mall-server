define(['@mixins/user', 'view/home/comps/EditUserDialog', 'view/home/comps/EditUserAvatarDialog'], function(user, EditUserDialog, EditUserAvatarDialog) {
  return {
    components: {EditUserDialog, EditUserAvatarDialog},
    mixins: [user],
    // 选项
    template: `
    <div class="n-frame">
        <div class="uinfo c_b">
            <div class="">
                <div class="main_l">
                    <div class="naInfoImgBox t_c">
                        <div class="na-img-area marauto">
                            <!--na-img-bg-area不能插入任何子元素-->
                            <div class="na-img-bg-area">
                            <img :src="$_user_userInfo.icon">
                            </div>
                            <em class="na-edit"></em>
                        </div>
                        <div class="naImgLink">
                            <a class="color4a9" 
                            @click="dialog.editUserAvatar = true"
                            href="javascript:void(0)" title="修改头像">修改头像</a>
                        </div>
                    </div>
                </div>
                <div class="main_r">
                    <div class="framedatabox">
                        <div class="fdata">
                            <a class="color4a9 fr" href="javascript:void(0)" title="编辑" @click="dialog.editUser = true">
                                <i class="iconpencil"></i>编辑</a>
                            <h3>基础资料</h3>
                        </div>
                        <div class="fdata lblnickname">
                            <p><span>姓名：</span><span class="value">{{$_user_userInfo.realName}}</span></p>
                        </div>
                        <div class="fdata lblbirthday">
                            <p><span>手机：</span><span class="value">{{$_user_userInfo.mobile}}</span></p>
                        </div>
                        <div class="fdata lblgender">
                            <p><span>邮箱：</span><span class="value">{{$_user_userInfo.email}}</span></p>
                        </div>
                        <div class="btn_editinfo">
                            <a class="btnadpt bg_normal" href="javascript:void(0)">编辑基础资料</a>
                        </div>
                    </div>
                </div>
            </div>
            <edit-user-dialog v-model="dialog.editUser"/>
            <edit-user-avatar-dialog v-model="dialog.editUserAvatar"/>
        </div>
    </div>
    `,
    data () {
      return {
        dialog: {
          editUser: false,
          editUserAvatar: false
        }
      }
    }
  }
})