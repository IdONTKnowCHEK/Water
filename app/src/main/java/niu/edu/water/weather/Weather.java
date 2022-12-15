package niu.edu.water.weather;

import java.util.List;

public class Weather {

    private String success;
    private ResultDTO result;
    private RecordsDTO records;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public RecordsDTO getRecords() {
        return records;
    }

    public void setRecords(RecordsDTO records) {
        this.records = records;
    }

    public static class ResultDTO {
        private String resource_id;
        private List<FieldsDTO> fields;

        public String getResource_id() {
            return resource_id;
        }

        public void setResource_id(String resource_id) {
            this.resource_id = resource_id;
        }

        public List<FieldsDTO> getFields() {
            return fields;
        }

        public void setFields(List<FieldsDTO> fields) {
            this.fields = fields;
        }

        public static class FieldsDTO {
            private String id;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class RecordsDTO {
        private List<LocationDTO> location;

        public List<LocationDTO> getLocation() {
            return location;
        }

        public void setLocation(List<LocationDTO> location) {
            this.location = location;
        }

        public static class LocationDTO {
            private String lat;
            private String lon;
            private String locationName;
            private String stationId;
            private TimeDTO time;
            private List<WeatherElementDTO> weatherElement;
            private List<ParameterDTO> parameter;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getLocationName() {
                return locationName;
            }

            public void setLocationName(String locationName) {
                this.locationName = locationName;
            }

            public String getStationId() {
                return stationId;
            }

            public void setStationId(String stationId) {
                this.stationId = stationId;
            }

            public TimeDTO getTime() {
                return time;
            }

            public void setTime(TimeDTO time) {
                this.time = time;
            }

            public List<WeatherElementDTO> getWeatherElement() {
                return weatherElement;
            }

            public void setWeatherElement(List<WeatherElementDTO> weatherElement) {
                this.weatherElement = weatherElement;
            }

            public List<ParameterDTO> getParameter() {
                return parameter;
            }

            public void setParameter(List<ParameterDTO> parameter) {
                this.parameter = parameter;
            }

            public static class TimeDTO {
                private String obsTime;

                public String getObsTime() {
                    return obsTime;
                }

                public void setObsTime(String obsTime) {
                    this.obsTime = obsTime;
                }
            }

            public static class WeatherElementDTO {
                private String elementName;
                private String elementValue;

                public String getElementName() {
                    return elementName;
                }

                public void setElementName(String elementName) {
                    this.elementName = elementName;
                }

                public String getElementValue() {
                    return elementValue;
                }

                public void setElementValue(String elementValue) {
                    this.elementValue = elementValue;
                }
            }

            public static class ParameterDTO {
                private String parameterName;
                private String parameterValue;

                public String getParameterName() {
                    return parameterName;
                }

                public void setParameterName(String parameterName) {
                    this.parameterName = parameterName;
                }

                public String getParameterValue() {
                    return parameterValue;
                }

                public void setParameterValue(String parameterValue) {
                    this.parameterValue = parameterValue;
                }
            }
        }
    }
}
