The percentile metrics result of SpringBoot Actuator integrated with Grails 4 is unexpected, and all the URI of the request is mapped to root.

For SpringBoot Actuator, the metrics is just the real request.

A similar description can be found in the following link:
https://stackoverflow.com/questions/66039125/obtaining-usable-grails-url-for-micrometer-metrics

### Task List

- [ ] Steps to reproduce provided
- [ ] Stacktrace (if present) provided
- [ ] Example that reproduces the problem uploaded to Github
- [ ] Full description of the issue provided (see below)

### Steps to Reproduce
1. using Grails 4.0.10, run `grails create-app grailsActuatorTest -profile rest-api` to create a project
2. add the following configuration to `grails-app/conf/application.yml`
  ```yaml
  management:
      endpoint:
          metrics:
              enabled: true
      endpoints:
          enabled-by-default: false
          web:
              exposure:
                  include:
                      - metrics
      metrics:
          distribution:
              percentiles:
                  http:
                      server:
                          requests: 0.80,0.90,0.99
  ```
3. add a new route in UrlMapping with the corresponding controller log, here is AccountController
4. start the application, and run the following request
```shell
curl http://localhost:9090/account?name=customer
curl http://localhost:9090/account?name=boss
 
curl http://localhost:9090/actuator/metrics/http.server.requests.percentile  

# it is better to format he result with jq 
curl http://localhost:9090/actuator/metrics/http.server.requests.percentile   | jq .

```

### Expected Behaviour
The `values` in tag: uri should the request of `/account` .

### Actual Behaviour
The unexpected `root` is in the `values` list . But the `/account` is disappeared.

```json
    {
      "tag": "uri",
      "values": [
        "/actuator/metrics/{requiredMetricName}",
        "/actuator/metrics/",
        "root"
      ]
    },
```

### Environment Information

- **Operating System**: macOS 11.4
- **Grails Version:** 4.0.10
- **JDK Version:** java "1.8.0_211"
- **Container Version (If Applicable):** NA

### Example Application

https://github.com:shengleee/grailsActuatorTest.git
