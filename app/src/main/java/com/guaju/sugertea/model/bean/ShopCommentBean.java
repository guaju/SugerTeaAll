package com.guaju.sugertea.model.bean;

import java.util.ArrayList;

/**
 * Created by guaju on 2017/10/13.
 */

public class ShopCommentBean {
    private String zongyeshu;
    private String zongtiaoshu;
    private String page;
    private ArrayList<Comment> list;

    public String getZongyeshu() {
        return zongyeshu;
    }

    public void setZongyeshu(String zongyeshu) {
        this.zongyeshu = zongyeshu;
    }

    public String getZongtiaoshu() {
        return zongtiaoshu;
    }

    public void setZongtiaoshu(String zongtiaoshu) {
        this.zongtiaoshu = zongtiaoshu;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ArrayList<Comment> getList() {
        return list;
    }

    public void setList(ArrayList<Comment> list) {
        this.list = list;
    }



    public  class Comment{


        /**
         * id : 415
         * ordernum : 201707261605501245
         * openid : 09f70a1bc8a4fee1c373a19b3e51fed1
         * beipinglunopenid : 20ebc25482ec8a3b206878fdd7c9835b
         * neirong : 非常满意，无可挑剔；
         * pingfen : 5
         * biaoqian :
         * shijian : 1506773353
         * fuwuid : 6911
         * shanghuid : 895
         * xianshi : 1
         * jinyongliyou :
         * shifoutongbu : 0
         * fenxiangshu : 0
         * dianzanshu : 0
         * pinglunshu : 0
         * location :
         * longitude :
         * latitude :
         * user : {"id":"16449","phone":"13522665310","openid":"09f70a1bc8a4fee1c373a19b3e51fed1","name":"秦红兵","nickname":null,"sex":"1","idnumber":null,"idpic":null,"avatar":null,"status":"1","wx_openid":null,"unionid":null,"longitude":null,"latitude":null,"regtime":"1499931908","logStringime":"1506775113","logStringimes":"1506773187","tjid":null,"beizhu":null,"profession":null,"zhiyeid":0,"birthday":"631123200","location":"","cid":null,"idpics":null,"idpicss":null,"if_work":"2","qqopenid":null,"shequid":null,"renzhengtime":null,"shanghunicheng":null,"zhiyezhao":null,"shenheshijian":null,"tuijian":0,"jinyongshijian":null,"shanghujinyong":0,"money":"0.00","xinxianshishu":0,"xuanshangshu":0,"wendashu":0,"huodongshu":0,"farenwushu":0,"jierenwushu":0,"xiadanshu":"8","jiedanshu":0,"renwupinglunshu":0,"renwupingfen":5,"fuwupinglunshu":0,"`fuwupingfen":5,"wanshanziliao":"2","jicika":0,"youhuiquan":0,"huiyuanka":"1","shoucangshanghu":"1"}
         */

        private String id;
        private String ordernum;
        private String openid;
        private String beipinglunopenid;
        private String neirong;
        private String pingfen;
        private String biaoqian;
        private String shijian;
        private String fuwuid;
        private String shanghuid;
        private String xianshi;
        private String jinyongliyou;
        private String shifoutongbu;
        private String fenxiangshu;
        private String dianzanshu;
        private String pinglunshu;
        private String location;
        private String longitude;
        private String latitude;
        private UserBean user;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getBeipinglunopenid() {
            return beipinglunopenid;
        }

        public void setBeipinglunopenid(String beipinglunopenid) {
            this.beipinglunopenid = beipinglunopenid;
        }

        public String getNeirong() {
            return neirong;
        }

        public void setNeirong(String neirong) {
            this.neirong = neirong;
        }

        public String getPingfen() {
            return pingfen;
        }

        public void setPingfen(String pingfen) {
            this.pingfen = pingfen;
        }

        public String getBiaoqian() {
            return biaoqian;
        }

        public void setBiaoqian(String biaoqian) {
            this.biaoqian = biaoqian;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getFuwuid() {
            return fuwuid;
        }

        public void setFuwuid(String fuwuid) {
            this.fuwuid = fuwuid;
        }

        public String getShanghuid() {
            return shanghuid;
        }

        public void setShanghuid(String shanghuid) {
            this.shanghuid = shanghuid;
        }

        public String getXianshi() {
            return xianshi;
        }

        public void setXianshi(String xianshi) {
            this.xianshi = xianshi;
        }

        public String getJinyongliyou() {
            return jinyongliyou;
        }

        public void setJinyongliyou(String jinyongliyou) {
            this.jinyongliyou = jinyongliyou;
        }

        public String getShifoutongbu() {
            return shifoutongbu;
        }

        public void setShifoutongbu(String shifoutongbu) {
            this.shifoutongbu = shifoutongbu;
        }

        public String getFenxiangshu() {
            return fenxiangshu;
        }

        public void setFenxiangshu(String fenxiangshu) {
            this.fenxiangshu = fenxiangshu;
        }

        public String getDianzanshu() {
            return dianzanshu;
        }

        public void setDianzanshu(String dianzanshu) {
            this.dianzanshu = dianzanshu;
        }

        public String getPinglunshu() {
            return pinglunshu;
        }

        public void setPinglunshu(String pinglunshu) {
            this.pinglunshu = pinglunshu;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public  class UserBean {
            /**
             * id : 16449
             * phone : 13522665310
             * openid : 09f70a1bc8a4fee1c373a19b3e51fed1
             * name : 秦红兵
             * nickname : null
             * sex : 1
             * idnumber : null
             * idpic : null
             * avatar : null
             * status : 1
             * wx_openid : null
             * unionid : null
             * longitude : null
             * latitude : null
             * regtime : 1499931908
             * logStringime : 1506775113
             * logStringimes : 1506773187
             * tjid : null
             * beizhu : null
             * profession : null
             * zhiyeid : 0
             * birthday : 631123200
             * location :
             * cid : null
             * idpics : null
             * idpicss : null
             * if_work : 2
             * qqopenid : null
             * shequid : null
             * renzhengtime : null
             * shanghunicheng : null
             * zhiyezhao : null
             * shenheshijian : null
             * tuijian : 0
             * jinyongshijian : null
             * shanghujinyong : 0
             * money : 0.00
             * xinxianshishu : 0
             * xuanshangshu : 0
             * wendashu : 0
             * huodongshu : 0
             * farenwushu : 0
             * jierenwushu : 0
             * xiadanshu : 8
             * jiedanshu : 0
             * renwupinglunshu : 0
             * renwupingfen : 5
             * fuwupinglunshu : 0
             * fuwupingfen : 5
             * wanshanziliao : 2
             * jicika : 0
             * youhuiquan : 0
             * huiyuanka : 1
             * shoucangshanghu : 1
             */

            private String id;
            private String phone;
            private String openid;
            private String name;
            private String nickname;
            private String sex;
            private Object idnumber;
            private Object idpic;
            private Object avatar;
            private String status;
            private Object wx_openid;
            private Object unionid;
            private Object longitude;
            private Object latitude;
            private String regtime;
            private String logStringime;
            private String logStringimes;
            private Object tjid;
            private Object beizhu;
            private Object profession;
            private String zhiyeid;
            private String birthday;
            private String location;
            private Object cid;
            private Object idpics;
            private Object idpicss;
            private String if_work;
            private Object qqopenid;
            private Object shequid;
            private Object renzhengtime;
            private Object shanghunicheng;
            private Object zhiyezhao;
            private Object shenheshijian;
            private String tuijian;
            private Object jinyongshijian;
            private String shanghujinyong;
            private String money;
            private String xinxianshishu;
            private String xuanshangshu;
            private String wendashu;
            private String huodongshu;
            private String farenwushu;
            private String jierenwushu;
            private String xiadanshu;
            private String jiedanshu;
            private String renwupinglunshu;
            private String renwupingfen;
            private String fuwupinglunshu;
            private String fuwupingfen;
            private String wanshanziliao;
            private String jicika;
            private String youhuiquan;
            private String huiyuanka;
            private String shoucangshanghu;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNickname() {
                return  nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public Object getIdnumber() {
                return idnumber;
            }

            public void setIdnumber(Object idnumber) {
                this.idnumber = idnumber;
            }

            public Object getIdpic() {
                return idpic;
            }

            public void setIdpic(Object idpic) {
                this.idpic = idpic;
            }

            public Object getAvatar() {
                return avatar;
            }

            public void setAvatar(Object avatar) {
                this.avatar = avatar;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getWx_openid() {
                return wx_openid;
            }

            public void setWx_openid(Object wx_openid) {
                this.wx_openid = wx_openid;
            }

            public Object getUnionid() {
                return unionid;
            }

            public void setUnionid(Object unionid) {
                this.unionid = unionid;
            }

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public Object getLatitude() {
                return latitude;
            }

            public void setLatitude(Object latitude) {
                this.latitude = latitude;
            }

            public String getRegtime() {
                return regtime;
            }

            public void setRegtime(String regtime) {
                this.regtime = regtime;
            }

            public String getLogStringime() {
                return logStringime;
            }

            public void setLogStringime(String logStringime) {
                this.logStringime = logStringime;
            }

            public String getLogStringimes() {
                return logStringimes;
            }

            public void setLogStringimes(String logStringimes) {
                this.logStringimes = logStringimes;
            }

            public Object getTjid() {
                return tjid;
            }

            public void setTjid(Object tjid) {
                this.tjid = tjid;
            }

            public Object getBeizhu() {
                return beizhu;
            }

            public void setBeizhu(Object beizhu) {
                this.beizhu = beizhu;
            }

            public Object getProfession() {
                return profession;
            }

            public void setProfession(Object profession) {
                this.profession = profession;
            }

            public String getZhiyeid() {
                return zhiyeid;
            }

            public void setZhiyeid(String zhiyeid) {
                this.zhiyeid = zhiyeid;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public Object getCid() {
                return cid;
            }

            public void setCid(Object cid) {
                this.cid = cid;
            }

            public Object getIdpics() {
                return idpics;
            }

            public void setIdpics(Object idpics) {
                this.idpics = idpics;
            }

            public Object getIdpicss() {
                return idpicss;
            }

            public void setIdpicss(Object idpicss) {
                this.idpicss = idpicss;
            }

            public String getIf_work() {
                return if_work;
            }

            public void setIf_work(String if_work) {
                this.if_work = if_work;
            }

            public Object getQqopenid() {
                return qqopenid;
            }

            public void setQqopenid(Object qqopenid) {
                this.qqopenid = qqopenid;
            }

            public Object getShequid() {
                return shequid;
            }

            public void setShequid(Object shequid) {
                this.shequid = shequid;
            }

            public Object getRenzhengtime() {
                return renzhengtime;
            }

            public void setRenzhengtime(Object renzhengtime) {
                this.renzhengtime = renzhengtime;
            }

            public Object getShanghunicheng() {
                return shanghunicheng;
            }

            public void setShanghunicheng(Object shanghunicheng) {
                this.shanghunicheng = shanghunicheng;
            }

            public Object getZhiyezhao() {
                return zhiyezhao;
            }

            public void setZhiyezhao(Object zhiyezhao) {
                this.zhiyezhao = zhiyezhao;
            }

            public Object getShenheshijian() {
                return shenheshijian;
            }

            public void setShenheshijian(Object shenheshijian) {
                this.shenheshijian = shenheshijian;
            }

            public String getTuijian() {
                return tuijian;
            }

            public void setTuijian(String tuijian) {
                this.tuijian = tuijian;
            }

            public Object getJinyongshijian() {
                return jinyongshijian;
            }

            public void setJinyongshijian(Object jinyongshijian) {
                this.jinyongshijian = jinyongshijian;
            }

            public String getShanghujinyong() {
                return shanghujinyong;
            }

            public void setShanghujinyong(String shanghujinyong) {
                this.shanghujinyong = shanghujinyong;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getXinxianshishu() {
                return xinxianshishu;
            }

            public void setXinxianshishu(String xinxianshishu) {
                this.xinxianshishu = xinxianshishu;
            }

            public String getXuanshangshu() {
                return xuanshangshu;
            }

            public void setXuanshangshu(String xuanshangshu) {
                this.xuanshangshu = xuanshangshu;
            }

            public String getWendashu() {
                return wendashu;
            }

            public void setWendashu(String wendashu) {
                this.wendashu = wendashu;
            }

            public String getHuodongshu() {
                return huodongshu;
            }

            public void setHuodongshu(String huodongshu) {
                this.huodongshu = huodongshu;
            }

            public String getFarenwushu() {
                return farenwushu;
            }

            public void setFarenwushu(String farenwushu) {
                this.farenwushu = farenwushu;
            }

            public String getJierenwushu() {
                return jierenwushu;
            }

            public void setJierenwushu(String jierenwushu) {
                this.jierenwushu = jierenwushu;
            }

            public String getXiadanshu() {
                return xiadanshu;
            }

            public void setXiadanshu(String xiadanshu) {
                this.xiadanshu = xiadanshu;
            }

            public String getJiedanshu() {
                return jiedanshu;
            }

            public void setJiedanshu(String jiedanshu) {
                this.jiedanshu = jiedanshu;
            }

            public String getRenwupinglunshu() {
                return renwupinglunshu;
            }

            public void setRenwupinglunshu(String renwupinglunshu) {
                this.renwupinglunshu = renwupinglunshu;
            }

            public String getRenwupingfen() {
                return renwupingfen;
            }

            public void setRenwupingfen(String renwupingfen) {
                this.renwupingfen = renwupingfen;
            }

            public String getFuwupinglunshu() {
                return fuwupinglunshu;
            }

            public void setFuwupinglunshu(String fuwupinglunshu) {
                this.fuwupinglunshu = fuwupinglunshu;
            }

            public String getFuwupingfen() {
                return fuwupingfen;
            }

            public void setFuwupingfen(String fuwupingfen) {
                this.fuwupingfen = fuwupingfen;
            }

            public String getWanshanziliao() {
                return wanshanziliao;
            }

            public void setWanshanziliao(String wanshanziliao) {
                this.wanshanziliao = wanshanziliao;
            }

            public String getJicika() {
                return jicika;
            }

            public void setJicika(String jicika) {
                this.jicika = jicika;
            }

            public String getYouhuiquan() {
                return youhuiquan;
            }

            public void setYouhuiquan(String youhuiquan) {
                this.youhuiquan = youhuiquan;
            }

            public String getHuiyuanka() {
                return huiyuanka;
            }

            public void setHuiyuanka(String huiyuanka) {
                this.huiyuanka = huiyuanka;
            }

            public String getShoucangshanghu() {
                return shoucangshanghu;
            }

            public void setShoucangshanghu(String shoucangshanghu) {
                this.shoucangshanghu = shoucangshanghu;
            }
        }
    }
}
