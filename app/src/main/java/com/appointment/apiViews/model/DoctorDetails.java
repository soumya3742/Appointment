package com.appointment.apiViews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DoctorDetails implements Serializable {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("education")
        @Expose
        private String education;
        @SerializedName("hospital_name")
        @Expose
        private String hospitalName;
        @SerializedName("clinic_address")
        @Expose
        private String clinicAddress;
        @SerializedName("doctor_name")
        @Expose
        private String doctorName;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("experience")
        @Expose
        private String experience;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("specialist_name")
        @Expose
        private String specialistName;
        @SerializedName("system_medicine")
        @Expose
        private String systemMedicine;
        @SerializedName("contact_no")
        @Expose
        private String contactNo;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("fees")
        @Expose
        private String fees;
        @SerializedName("images")
        @Expose
        private String image;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitute")
        @Expose
        private String longitute;
        @SerializedName("timings")
        @Expose
        private List<Timing> timings = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getClinicAddress() {
            return clinicAddress;
        }

        public void setClinicAddress(String clinicAddress) {
            this.clinicAddress = clinicAddress;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSpecialistName() {
            return specialistName;
        }

        public void setSpecialistName(String specialistName) {
            this.specialistName = specialistName;
        }

        public String getSystemMedicine() {
            return systemMedicine;
        }

        public void setSystemMedicine(String systemMedicine) {
            this.systemMedicine = systemMedicine;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFees() {
            return fees;
        }

        public void setFees(String fees) {
            this.fees = fees;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitute() {
            return longitute;
        }

        public void setLongitute(String longitute) {
            this.longitute = longitute;
        }

        public List<Timing> getTimings() {
            return timings;
        }

        public void setTimings(List<Timing> timings) {
            this.timings = timings;
        }


        public class Timing implements Serializable {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("doctor_id")
            @Expose
            private String doctorId;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("hospital_name")
            @Expose
            private String hospitalName;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("timing")
            @Expose
            private String timing;
            @SerializedName("fees")
            @Expose
            private String fees;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(String doctorId) {
                this.doctorId = doctorId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTiming() {
                return timing;
            }

            public void setTiming(String timing) {
                this.timing = timing;
            }

            public String getFees() {
                return fees;
            }

            public void setFees(String fees) {
                this.fees = fees;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }
        }
    }

}
