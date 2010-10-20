package comm.bean;

public class ClientInfo {
        private String ip;
        private String mac;
        private String systemLanguage;
        private String systemType;
        private String browserLanguage;
        private String browserType;
        private String cpuClass;// CPU类型
        private String color;// 颜色
        private String resolution;// 分辨率

        public String getIp() {
                return ip;
        }

        public void setIp(String ip) {
                this.ip = ip;
        }

        public String getMac() {
                return mac;
        }

        public void setMac(String mac) {
                this.mac = mac;
        }

        public String getSystemLanguage() {
                return systemLanguage;
        }

        public void setSystemLanguage(String systemLanguage) {
                this.systemLanguage = systemLanguage;
        }

        public String getSystemType() {
                return systemType;
        }

        public void setSystemType(String systemType) {
                this.systemType = systemType;
        }

        public String getBrowserLanguage() {
                return browserLanguage;
        }

        public void setBrowserLanguage(String browserLanguage) {
                this.browserLanguage = browserLanguage;
        }

        public String getBrowserType() {
                return browserType;
        }

        public void setBrowserType(String browserType) {
                this.browserType = browserType;
        }

        public String getCpuClass() {
                return cpuClass;
        }

        public void setCpuClass(String cpuClass) {
                this.cpuClass = cpuClass;
        }

        public String getColor() {
                return color;
        }

        public void setColor(String color) {
                this.color = color;
        }

        public String getResolution() {
                return resolution;
        }

        public void setResolution(String resolution) {
                this.resolution = resolution;
        }

        public String toString() {
                return "ip=" + ip + ";mac=" + mac + ";browserLanguage="
                                + browserLanguage + ";browserType=" + browserType
                                + ";systemLanguage=" + systemLanguage + ";systemType="
                                + systemType + ";cpuClass=" + cpuClass + ";resolution="
                                + resolution + ";color=" + color;
        }
}
