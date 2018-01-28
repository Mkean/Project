package bean;

/**
 * 作者：王庆
 * 时间：2018/1/6
 */

public class UpdateBean {

    /**
     * msg : 更新成功
     * code : 0
     * data : {"addr":"啊发发沙发上大师","addrid":820,"mobile":18612991023,"name":"Web","status":0,"uid":2825}
     */

    private String msg;
    private String code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * addr : 啊发发沙发上大师
         * addrid : 820
         * mobile : 18612991023
         * name : Web
         * status : 0
         * uid : 2825
         */

        private String addr;
        private String addrid;
        private String mobile;
        private String name;
        private String status;
        private String uid;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getAddrid() {
            return addrid;
        }

        public void setAddrid(String addrid) {
            this.addrid = addrid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
