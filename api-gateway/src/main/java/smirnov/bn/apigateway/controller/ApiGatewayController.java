package smirnov.bn.apigateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import smirnov.bn.apigateway.info_model_patterns.*;

@RestController
@RequestMapping("/gateway_API")
public class ApiGatewayController {

    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";

    private static final String SERVICE_1_PORT_STRING = "8191";
    private static final String SERVICE_1_URI_COMMON_DIR_STRING = "/ling_var_dict";
    private static final String SERVICE_1_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_1_PORT_STRING + SERVICE_1_URI_COMMON_DIR_STRING;

    private static final String CREATE_LNG_VR_POST_URI_STRING = "/create-ling_var";
    private static final String READ_BY_ID_LNG_VR_GET_URI_STRING = "/read-";
    private static final String READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING = "/read-by-emp-uuid-";
    private static final String READ_ALL_LNG_VR_GET_URI_STRING = "/read-all";
    private static final String READ_ALL_PGNTD_LNG_VR_GET_URI_STRING = "/read-all-paginated";
    private static final String UPDATE_BY_ID_LNG_VR_PUT_URI_STRING = "/update-ling_var";
    private static final String DELETE_LNG_VR_DELETE_URI_STRING = "/delete-";

    private static final String CREATE_LNG_VR_POST_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + CREATE_LNG_VR_POST_URI_STRING;
    private static final String READ_BY_ID_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_BY_ID_LNG_VR_GET_URI_STRING;
    private static final String READ_BY_EMP_UUID_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING;
    private static final String READ_ALL_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_ALL_LNG_VR_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_LNG_VR_GET_URI_STRING;
    private static final String UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + UPDATE_BY_ID_LNG_VR_PUT_URI_STRING;
    private static final String DELETE_LNG_VR_DELETE_URI_TMPLT = SERVICE_1_ABS_URI_COMMON_STRING + DELETE_LNG_VR_DELETE_URI_STRING;

    private static final String SERVICE_2_PORT_STRING = "8192";
    private static final String SERVICE_2_URI_COMMON_DIR_STRING = "/biz_proc_desc/";
    private static final String SERVICE_2_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_2_PORT_STRING + SERVICE_2_URI_COMMON_DIR_STRING;

    private static final String CREATE_BP_DSC_POST_URI_STRING = "create-biz_proc_desc";
    private static final String READ_BY_ID_BP_DSC_GET_URI_STRING = "read-";
    private static final String READ_BY_EMP_UUID_BP_DSC_GET_URI_STRING = "read-by-emp-uuid-";
    private static final String READ_ALL_BP_DSC_GET_URI_STRING = "read-all";
    private static final String READ_ALL_PGNTD_BP_DSC_GET_URI_STRING = "read-all-paginated";
    private static final String UPDATE_BY_ID_BP_DSC_PUT_URI_STRING = "update-biz_proc_desc";
    private static final String DELETE_BP_DSC_DELETE_URI_STRING = "delete-";

    private static final String CREATE_BP_DSC_POST_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + CREATE_BP_DSC_POST_URI_STRING;
    private static final String READ_BY_ID_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_BY_ID_BP_DSC_GET_URI_STRING;
    private static final String READ_BY_EMP_UUID_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_BY_EMP_UUID_BP_DSC_GET_URI_STRING;
    private static final String READ_ALL_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_ALL_BP_DSC_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_BP_DSC_GET_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_BP_DSC_GET_URI_STRING;
    private static final String UPDATE_BY_ID_BP_DSC_PUT_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + UPDATE_BY_ID_BP_DSC_PUT_URI_STRING;
    private static final String DELETE_BP_DSC_DELETE_URI_TMPLT = SERVICE_2_ABS_URI_COMMON_STRING + DELETE_BP_DSC_DELETE_URI_STRING;

    private static final String SERVICE_3_PORT_STRING = "8193";
    private static final String SERVICE_3_URI_COMMON_DIR_STRING = "/employees/";
    private static final String SERVICE_3_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_3_PORT_STRING + SERVICE_3_URI_COMMON_DIR_STRING;

    private static final String CREATE_EMP_POST_URI_STRING = "create-employee";
    //private static final String READ_BY_UUID_EMP_GET_URI_STRING = "read-";
    private static final String READ_BY_EMP_UUID_EMP_GET_URI_STRING = "read-by-emp-uuid-";
    private static final String READ_ALL_EMP_GET_URI_STRING = "read-all";
    private static final String READ_ALL_PGNTD_EMP_GET_URI_STRING = "read-all-paginated";
    private static final String UPDATE_BY_UUID_EMP_PUT_URI_STRING = "update-employee";
    private static final String DELETE_EMP_DELETE_URI_STRING = "delete-";

    private static final String CREATE_EMP_POST_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + CREATE_EMP_POST_URI_STRING;
    private static final String READ_BY_UUID_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_BY_EMP_UUID_EMP_GET_URI_STRING; //READ_BY_UUID_EMP_GET_URI_STRING;
    private static final String READ_ALL_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_ALL_EMP_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_EMP_GET_URI_STRING;
    private static final String UPDATE_BY_UUID_EMP_PUT_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + UPDATE_BY_UUID_EMP_PUT_URI_STRING;
    private static final String DELETE_EMP_DELETE_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + DELETE_EMP_DELETE_URI_STRING;

    private static final String SCRT_SERVICE_PORT_STRING = "8202";
    private static final String SCRT_SERVICE_URI_COMMON_DIR_STRING = "/security_service";
    private static final String SCRT_SERVICE_AUTH_URI_COMMON_DIR_STRING = "/security_service/authorization";

    private static final String SCRT_SERVICE_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SCRT_SERVICE_PORT_STRING + SCRT_SERVICE_URI_COMMON_DIR_STRING;
    private static final String SCRT_AUTH_SERVICE_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SCRT_SERVICE_PORT_STRING + SCRT_SERVICE_AUTH_URI_COMMON_DIR_STRING;

    private static final String CREATE_USER_POST_URI_STRING = "/create-user";
    private static final String CREATE_AUTH_CODE_POST_URI_STRING = "/create-auth-code";
    private static final String CREATE_ACCESS_TOKEN_POST_URI_STRING = "/create-access-token";
    //private static final String READ_BY_ID_USER_GET_URI_STRING = "/read-";
    private static final String READ_BY_USR_UUID_USER_GET_URI_STRING = "/read-by-usr-uuid-";
    private static final String READ_BY_LGN_EML_USER_GET_URI_STRING = "/read-by-usr-login-";
    private static final String READ_ALL_USER_GET_URI_STRING = "/read-all";
    private static final String READ_ALL_PGNTD_USER_GET_URI_STRING = "/read-all-paginated";
    private static final String UPDATE_BY_ID_USER_PUT_URI_STRING = "/update-user";
    private static final String DELETE_USER_DELETE_URI_STRING = "/delete-";

    private static final String CREATE_USER_POST_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + CREATE_USER_POST_URI_STRING; //private static final String READ_BY_ID_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_BY_ID_USER_GET_URI_STRING;
    private static final String CREATE_AUTH_CODE_POST_URI_TMPLT = SCRT_AUTH_SERVICE_ABS_URI_COMMON_STRING + CREATE_AUTH_CODE_POST_URI_STRING;
    private static final String CREATE_ACCESS_TOKEN_POST_URI_TMPLT = SCRT_AUTH_SERVICE_ABS_URI_COMMON_STRING + CREATE_ACCESS_TOKEN_POST_URI_STRING;
    private static final String READ_BY_USR_UUID_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_BY_USR_UUID_USER_GET_URI_STRING;
    private static final String READ_BY_LGN_EML_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_BY_LGN_EML_USER_GET_URI_STRING;
    private static final String READ_ALL_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_ALL_USER_GET_URI_STRING;
    private static final String READ_ALL_PGNTD_USER_GET_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + READ_ALL_PGNTD_USER_GET_URI_STRING;
    private static final String UPDATE_BY_ID_USER_PUT_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + UPDATE_BY_ID_USER_PUT_URI_STRING;
    private static final String DELETE_USER_DELETE_URI_TMPLT = SCRT_SERVICE_ABS_URI_COMMON_STRING + DELETE_USER_DELETE_URI_STRING;


    //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayController.class);

    //https://stackoverflow.com/questions/14726082/spring-mvc-rest-service-redirect-forward-proxy (:)
    @ResponseBody
    private <T> ResponseEntity<String> proxingExternalRequests(@RequestBody(required = false) T body,
                                                               HttpMethod method, HttpServletRequest request,
                                                               String microServiceURIString) //, HttpServletResponse response)
            throws URISyntaxException {

        URI uri = new URI(microServiceURIString);

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        HttpEntity<T> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(uri, method, httpEntity, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }

    //**********************************************************************************************************************
    //*******************************SERVICE_1_API_PROXING******************************************************************
    //**********************************************************************************************************************
    //"/ling_var_dict/create-ling_var" (:)
    @PostMapping(SERVICE_1_URI_COMMON_DIR_STRING + CREATE_LNG_VR_POST_URI_STRING)
    public ResponseEntity<String> createLingVar(HttpServletRequest request, @RequestBody LingVarInfo lingVarInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createLingVar() - START");
        return this.proxingExternalRequests(lingVarInfo, HttpMethod.POST, request,
                CREATE_LNG_VR_POST_URI_TMPLT);
    }

    //"/ling_var_dict/read-{id}" (:)
    @GetMapping(SERVICE_1_URI_COMMON_DIR_STRING + READ_BY_ID_LNG_VR_GET_URI_STRING + "{id}")
    public ResponseEntity<String> findLingVarById(HttpServletRequest request, @PathVariable Integer id)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findLingVarById() - START" + "\n" + "id param: " + String.valueOf(id));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_ID_LNG_VR_GET_URI_TMPLT + String.valueOf(id));
    }

    //"/ling_var_dict/read-by-emp-uuid-{employeeUuid}" (:)
    @GetMapping(SERVICE_1_URI_COMMON_DIR_STRING + READ_BY_EMP_UUID_LNG_VR_GET_URI_STRING + "{employeeUuid}")
    public ResponseEntity<String> findLingVarsByEmployeeUuid(HttpServletRequest request, @PathVariable String employeeUuid)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findLingVarsByEmployeeUuid() - START" + "\n" + "id param: " + employeeUuid);
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_EMP_UUID_LNG_VR_GET_URI_TMPLT + employeeUuid);
    }

    //"/ling_var_dict/read-all" (:)
    @GetMapping(SERVICE_1_URI_COMMON_DIR_STRING + READ_ALL_LNG_VR_GET_URI_STRING)
    public ResponseEntity<String> findAllLingVars(HttpServletRequest request)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllLingVars() - START");
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_LNG_VR_GET_URI_TMPLT);
    }

    //"/ling_var_dict/read-all-paginated" (:)
    @RequestMapping(value = SERVICE_1_URI_COMMON_DIR_STRING + READ_ALL_PGNTD_LNG_VR_GET_URI_STRING, params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<String> findAllLingVarPaginated(HttpServletRequest request,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllLingVarPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                "sizeLimit param: " + String.valueOf(sizeLimit));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_PGNTD_LNG_VR_GET_URI_TMPLT + "?" + "page=" + String.valueOf(page) + "&" +
                        "sizeLimit=" + String.valueOf(sizeLimit));
    }

    //"/ling_var_dict/update-ling_var" (:)
    @PutMapping(SERVICE_1_URI_COMMON_DIR_STRING + UPDATE_BY_ID_LNG_VR_PUT_URI_STRING)
    public ResponseEntity<String> updateLingVar(HttpServletRequest request, @RequestBody LingVarInfo lingVarInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller updateLingVar() - START" + "\n" + "id param: " + String.valueOf(lingVarInfo.getLingVarId()));
        return this.proxingExternalRequests(lingVarInfo, HttpMethod.PUT, request,
                UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT);
    }

    //"/ling_var_dict/delete-{lingVarId}" (:)
    @DeleteMapping(SERVICE_1_URI_COMMON_DIR_STRING + DELETE_LNG_VR_DELETE_URI_STRING + "{lingVarId}")
    public ResponseEntity<String> deleteLingVarById(HttpServletRequest request, @PathVariable Integer lingVarId)
            throws URISyntaxException {
        logger.info("API_Gateway_controller deleteLingVarById() - START" + "\n" + "id param: " + String.valueOf(lingVarId));
        return this.proxingExternalRequests(null, HttpMethod.DELETE, request,
                DELETE_LNG_VR_DELETE_URI_TMPLT + String.valueOf(lingVarId));
    }

    //**********************************************************************************************************************
    //*******************************SERVICE_2_API_PROXING******************************************************************
    //**********************************************************************************************************************
    //"/biz_proc_desc/create-biz_proc_desc" (:)
    @PostMapping(SERVICE_2_URI_COMMON_DIR_STRING + CREATE_BP_DSC_POST_URI_STRING)
    public ResponseEntity<String> createBusinessProcDesc(HttpServletRequest request, @RequestBody BusinessProcDescInfo businessProcDescInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createBusinessProcDesc() - START");
        return this.proxingExternalRequests(businessProcDescInfo, HttpMethod.POST, request,
                CREATE_BP_DSC_POST_URI_TMPLT);
    }

    //"/biz_proc_desc/read-{id}" (:)
    @GetMapping(SERVICE_2_URI_COMMON_DIR_STRING + READ_BY_ID_BP_DSC_GET_URI_STRING + "{id}")
    public ResponseEntity<String> findBusinessProcDescById(HttpServletRequest request, @PathVariable Integer id)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findBusinessProcDescById() - START");
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_ID_BP_DSC_GET_URI_TMPLT + String.valueOf(id));
    }

    //"/biz_proc_desc/read-all" (:)
    @GetMapping(SERVICE_2_URI_COMMON_DIR_STRING + READ_ALL_BP_DSC_GET_URI_STRING)
    public ResponseEntity<String> findAllBusinessProcDescs(HttpServletRequest request)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllBusinessProcDescs() - START");
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_BP_DSC_GET_URI_TMPLT);
    }

    //"/biz_proc_desc/read-all-paginated" (:)
    @RequestMapping(value = SERVICE_2_URI_COMMON_DIR_STRING + READ_ALL_PGNTD_BP_DSC_GET_URI_STRING, params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<String> findAllBusinessProcDescPaginated(HttpServletRequest request,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllBusinessProcDescPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                "sizeLimit param: " + String.valueOf(sizeLimit));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_PGNTD_BP_DSC_GET_URI_TMPLT + "?" + "page=" + String.valueOf(page) + "&" +
                        "sizeLimit=" + String.valueOf(sizeLimit));
    }

    //"/biz_proc_desc/update-biz_proc_desc" (:)
    @PutMapping(SERVICE_2_URI_COMMON_DIR_STRING + UPDATE_BY_ID_BP_DSC_PUT_URI_STRING)
    public ResponseEntity<String> updateBusinessProcDesc(HttpServletRequest request, @RequestBody BusinessProcDescInfo businessProcDescInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller updateBusinessProcDesc() - START" + "\n" + "id param: "
                + String.valueOf(businessProcDescInfo.getBizProcId()));
        return this.proxingExternalRequests(businessProcDescInfo, HttpMethod.PUT, request,
                UPDATE_BY_ID_BP_DSC_PUT_URI_TMPLT);
    }

    //"/biz_proc_desc/delete-{bizProcId}" (:)
    @DeleteMapping(SERVICE_2_URI_COMMON_DIR_STRING + DELETE_BP_DSC_DELETE_URI_STRING + "{id}")
    public ResponseEntity<String> deleteBusinessProcDescById(HttpServletRequest request, @PathVariable("id") Integer bizProcId)
            throws URISyntaxException {
        logger.info("API_Gateway_controller deleteBusinessProcDescById() - START" + "\n" + "id param: " + String.valueOf(bizProcId));
        return this.proxingExternalRequests(null, HttpMethod.DELETE, request,
                DELETE_BP_DSC_DELETE_URI_TMPLT + String.valueOf(bizProcId));
    }

    //**********************************************************************************************************************
    //*******************************SERVICE_3_API_PROXING******************************************************************
    //**********************************************************************************************************************
    //"/employees/create-employee" (:)
    @PostMapping(SERVICE_3_URI_COMMON_DIR_STRING + CREATE_EMP_POST_URI_STRING)
    public ResponseEntity<String> createEmployee(HttpServletRequest request, @RequestBody EmployeeInfo employeeInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createEmployee() - START");
        return this.proxingExternalRequests(employeeInfo, HttpMethod.POST, request,
                CREATE_EMP_POST_URI_TMPLT);
    }

    //"/employees/read-by-emp-uuid-{uuid}" ////"/employees/read-{id}" (:)
    @GetMapping(SERVICE_3_URI_COMMON_DIR_STRING + READ_BY_EMP_UUID_EMP_GET_URI_STRING + "{uuid}")
    public ResponseEntity<String> findEmployeeByUuid(HttpServletRequest request, @PathVariable("uuid") String employeeUuid)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findEmployeeByUuid() - START" + "\n" + "employeeUuid param: " + UUID.fromString(employeeUuid));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_UUID_EMP_GET_URI_TMPLT + employeeUuid);
    }

    //"/employees/read-all" (:) //http://localhost:8194/gateway_API/employees/read-all/
    @GetMapping(SERVICE_3_URI_COMMON_DIR_STRING + READ_ALL_EMP_GET_URI_STRING)
    public ResponseEntity<String> findAllEmployees(HttpServletRequest request)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllEmployees() - START");
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_EMP_GET_URI_TMPLT);
        //ResponseEntity.status(401).build();
    }

    //"/employees/read-all-paginated" (:)
    @RequestMapping(value = SERVICE_3_URI_COMMON_DIR_STRING + READ_ALL_PGNTD_EMP_GET_URI_STRING, params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<String> findAllEmployeesPaginated(HttpServletRequest request,
                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllEmployeesPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                "sizeLimit param: " + String.valueOf(sizeLimit));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_PGNTD_EMP_GET_URI_TMPLT + "?" + "page=" + String.valueOf(page) + "&" +
                        "sizeLimit=" + String.valueOf(sizeLimit));
    }

    //"/employees/update-employee" (:)
    @PutMapping(SERVICE_3_URI_COMMON_DIR_STRING + UPDATE_BY_UUID_EMP_PUT_URI_STRING)
    public ResponseEntity<String> updateEmployee(HttpServletRequest request, @RequestBody EmployeeInfo employeeInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller updateEmployee() - START" + "\n" + "employeeUuid param: " + String.valueOf(employeeInfo.getEmployeeUuid()));
        return this.proxingExternalRequests(employeeInfo, HttpMethod.PUT, request,
                UPDATE_BY_UUID_EMP_PUT_URI_TMPLT);
    }

    //"/employees/delete-{employeeUuid}" (:)
    @DeleteMapping(SERVICE_3_URI_COMMON_DIR_STRING + DELETE_EMP_DELETE_URI_STRING + "{uuid}")
    public ResponseEntity<String> deleteEmployeeByUuid(HttpServletRequest request, @PathVariable("uuid") UUID employeeUuid)
            throws URISyntaxException {
        logger.info("API_Gateway_controller deleteEmployeeByUuid() - START" + "\n" + "employeeUuid param: " + String.valueOf(employeeUuid));
        return this.proxingExternalRequests(null, HttpMethod.DELETE, request,
                DELETE_EMP_DELETE_URI_TMPLT + employeeUuid.toString());
    }

    //**********************************************************************************************************************
    //*******************************MULTI_SERVICES_API*********************************************************************
    //**********************************************************************************************************************
    ///ling_var AND employee's info read-all (:)
    @GetMapping("/ling_var_and_employee/read-all")
    public ResponseEntity<List<LingVarWithEmployeeInfo>> findAllLingVarWithEmployeeData() {
        try {
            logger.info("findAllLingVarWithEmployeeData() - START");

            //Сначала получаем данные по всем Лингвистическим переменным:
            //[
            //https://www.baeldung.com/spring-rest-template-list
            //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
            //https://stackoverflow.com/questions/15218462/how-to-aggregate-jax-rs-responses-from-multiple-services
            //]
            ResponseEntity<List<LingVarInfo>> lingVarInfoResponse =
                    restTemplate.exchange(READ_ALL_LNG_VR_GET_URI_TMPLT, //"http://localhost:8191/ling_var_dict/read-all",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<LingVarInfo>>() {
                            });
            List<LingVarInfo> lingVarInfoList = lingVarInfoResponse.getBody();

            //Затем получаем данные по всем Сотрудникам:
            ResponseEntity<List<EmployeeInfo>> employeeInfoResponse =
                    restTemplate.exchange(READ_ALL_EMP_GET_URI_TMPLT, //"http://localhost:8193/employees/read-all",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeInfo>>() {
                            });
            List<EmployeeInfo> employeeInfoList = employeeInfoResponse.getBody();

            //Наконец, для каждой Лингвистической переменной получаем информацию об использовавшем её Сотруднике,
            //технчески осуществляя INNER JOIN Лингвистических переменных и Сотрудниках
            //(см. подробности join-а тут:
            //https://stackoverflow.com/questions/37003220/inner-join-of-two-arraylists-purely-in-java):
            final Map<String, EmployeeInfo> employeeInfoListMappedById = employeeInfoList.stream()
                    .collect(Collectors.toMap(k -> k.getEmployeeUuid().toString(), k -> k));

            final List<LingVarWithEmployeeInfo> lingVarWithEmployeeInfoList = lingVarInfoList.stream()
                    .map(lingVarInfo -> new LingVarWithEmployeeInfo(lingVarInfo,
                            employeeInfoListMappedById.get(lingVarInfo.getEmployeeUuid())))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(lingVarWithEmployeeInfoList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findAllLingVarWithEmployeeData(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    ///ling_var AND employee's info update (:)
    @PutMapping("/ling_var_and_employee/update-ling_var_and_employee")
    public ResponseEntity<String> updateEmployeeWithLingVarData(HttpServletRequest request, @RequestBody LingVarWithEmployeeInfo lingVarWithEmployeeInfo) {
        try {
            logger.info("updateEmployeeWithLingVarData() - START");

            //Сначала изменяем данные по сотруднику (:)
            EmployeeInfo employeeInfo = new EmployeeInfo();
            //N.B.: employeeId никак не будет затронут в процессе обновления, поэтому можем передать сюда null:
            employeeInfo.setEmployeeId(null); //lingVarWithEmployeeInfo.getEmployeeId());
            employeeInfo.setEmployeeName(lingVarWithEmployeeInfo.getEmployeeName());
            employeeInfo.setEmployeeEmail(lingVarWithEmployeeInfo.getEmployeeEmail());
            employeeInfo.setEmployeeLogin(lingVarWithEmployeeInfo.getEmployeeLogin());
            employeeInfo.setEmployeeUuid(UUID.fromString(lingVarWithEmployeeInfo.getEmployeeUuid()));

            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
            HttpHeaders employeeInfoHeaders = new HttpHeaders();
            employeeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EmployeeInfo> requestEmployeeInfoEntity = new HttpEntity<>(employeeInfo, employeeInfoHeaders);
            restTemplate.exchange(UPDATE_BY_UUID_EMP_PUT_URI_TMPLT,
                    HttpMethod.PUT, requestEmployeeInfoEntity, new ParameterizedTypeReference<List<EmployeeInfo>>() {
                    });

            //Затем получаем и потом изменяем данные по всем Лингвистическим переменным (на одинаковые данные, передаваемые в lingVarWithEmployeeInfo),
            //связанным с данным сотрудником по UUID, который также передаётся в lingVarWithEmployeeInfo (:)
            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
            //HttpHeaders lingVarInfoHeaders = new HttpHeaders();
            //employeeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
            //HttpEntity<EmployeeInfo> requestLingVarInfoEntity = new HttpEntity<>(lingVarInfo, lingVarInfoHeaders);
            ResponseEntity<List<LingVarInfo>> lingVarInfoResponse =
                    restTemplate.exchange(READ_BY_EMP_UUID_LNG_VR_GET_URI_TMPLT
                                    + String.valueOf(employeeInfo.getEmployeeUuid()), //"http://localhost:8191/ling_var_dict/read-by-emp-uuid-{employeeUuid}",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<LingVarInfo>>() {
                            });
            List<LingVarInfo> lingVarInfoList = lingVarInfoResponse.getBody();

            for (LingVarInfo lingVarInfo : lingVarInfoList) {
                logger.info("Another lingVarInfo in lingVarInfoList in updateEmployeeWithLingVarData() params"
                        + "\n" + "Id param: " + String.valueOf(lingVarInfo.getLingVarId())
                        + "\n" + "LingVarName param: " + lingVarInfo.getLingVarName()
                        + "\n" + "EmployeeUuid param: " + lingVarInfo.getEmployeeUuid()
                        + "\n" + "LingVarTermLowVal param: " + String.valueOf(lingVarInfo.getLingVarTermLowVal())
                        + "\n" + "LingVarTermMedVal param: " + String.valueOf(lingVarInfo.getLingVarTermMedVal())
                        + "\n" + "LingVarTermHighVal param: " + String.valueOf(lingVarInfo.getLingVarTermHighVal()));

                lingVarInfo.setLingVarName(lingVarWithEmployeeInfo.getLingVarName());
                lingVarInfo.setLingVarTermLowVal(lingVarWithEmployeeInfo.getLingVarTermLowVal());
                lingVarInfo.setLingVarTermMedVal(lingVarWithEmployeeInfo.getLingVarTermMedVal());
                lingVarInfo.setLingVarTermHighVal(lingVarWithEmployeeInfo.getLingVarTermHighVal());

                // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
                HttpHeaders lingVarInfoHeaders = new HttpHeaders();
                lingVarInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<LingVarInfo> requestLingVarInfoEntity = new HttpEntity<>(lingVarInfo, lingVarInfoHeaders);
                restTemplate.exchange(UPDATE_BY_ID_LNG_VR_PUT_URI_TMPLT,
                        HttpMethod.PUT, requestLingVarInfoEntity, new ParameterizedTypeReference<List<LingVarInfo>>() {
                        });

                logger.info("Another lingVarInfo in lingVarInfoList in updateEmployeeWithLingVarData() UPDATED");
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateEmployeeWithLingVarData(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    ///ling_var AND employee's info update (:)
    @PutMapping("/biz_proc_desc/update-/biz_proc_desc_and_employee")
    public ResponseEntity<String> updateEmployeeWithBizDescData(HttpServletRequest request, @RequestBody BizDescWithEmployeeInfo bizDescWithEmployeeInfo) {
        try {
            logger.info("updateEmployeeWithBizDescData() - START");

            //Сначала изменяем данные по сотруднику (:)
            EmployeeInfo employeeInfo = new EmployeeInfo();
            //N.B.: employeeId никак не будет затронут в процессе обновления, поэтому можем передать сюда null:
            employeeInfo.setEmployeeId(null); //BizDescWithEmployeeInfo.getEmployeeId());
            employeeInfo.setEmployeeName(bizDescWithEmployeeInfo.getEmployeeName());
            employeeInfo.setEmployeeEmail(bizDescWithEmployeeInfo.getEmployeeEmail());
            employeeInfo.setEmployeeLogin(bizDescWithEmployeeInfo.getEmployeeLogin());
            employeeInfo.setEmployeeUuid(UUID.fromString(bizDescWithEmployeeInfo.getEmployeeUuid()));

            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
            HttpHeaders employeeInfoHeaders = new HttpHeaders();
            employeeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EmployeeInfo> requestEmployeeInfoEntity = new HttpEntity<>(employeeInfo, employeeInfoHeaders);
            restTemplate.exchange(UPDATE_BY_UUID_EMP_PUT_URI_TMPLT,
                    HttpMethod.PUT, requestEmployeeInfoEntity, new ParameterizedTypeReference<List<EmployeeInfo>>() {
                    });

            //Затем получаем и потом изменяем данные по всем Описаниям Бизнес-процессов
            //(на одинаковые данные, передаваемые в businessProcDescInfo),
            //связанным с данным сотрудником по UUID, который также передаётся в businessProcDescInfo (:)
            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
            //HttpHeaders businessProcDescInfoHeaders = new HttpHeaders();
            //employeeInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
            //HttpEntity<EmployeeInfo> requestBusinessProcDescInfoEntity = new HttpEntity<>(businessProcDescInfo, businessProcDescInfoHeaders);
            ResponseEntity<List<BusinessProcDescInfo>> businessProcDescInfoResponse =
                    restTemplate.exchange(READ_BY_EMP_UUID_BP_DSC_GET_URI_TMPLT
                                    + String.valueOf(employeeInfo.getEmployeeUuid()),
                            //"http://localhost:8191/ling_var_dict/read-by-emp-uuid-{employeeUuid}",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<BusinessProcDescInfo>>() {
                            });
            List<BusinessProcDescInfo> businessProcDescInfoList = businessProcDescInfoResponse.getBody();

            for (BusinessProcDescInfo businessProcDescInfo : businessProcDescInfoList) {
                logger.info("Another businessProcDescInfo in businessProcDescInfoList in updateEmployeeWithBizDescData() params"
                        + "\n" + "Id param: " + String.valueOf(businessProcDescInfo.getBizProcId())
                        + "\n" + "BizProcName param: " + businessProcDescInfo.getBizProcName()
                        + "\n" + "EmployeeUuid param: " + businessProcDescInfo.getEmployeeUuid()
                        + "\n" + "BizProcDescStr param: " + String.valueOf(businessProcDescInfo.getBizProcDescStr()));

                businessProcDescInfo.setBizProcName(bizDescWithEmployeeInfo.getBizProcName());
                businessProcDescInfo.setBizProcDescStr(bizDescWithEmployeeInfo.getBizProcDescStr());

                // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html (:)
                HttpHeaders businessProcDescInfoHeaders = new HttpHeaders();
                businessProcDescInfoHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<BusinessProcDescInfo> requestBusinessProcDescInfoEntity =
                        new HttpEntity<>(businessProcDescInfo, businessProcDescInfoHeaders);
                restTemplate.exchange(UPDATE_BY_ID_BP_DSC_PUT_URI_TMPLT,
                        HttpMethod.PUT, requestBusinessProcDescInfoEntity, new ParameterizedTypeReference<List<BusinessProcDescInfo>>() {
                        });

                logger.info("Another businessProcDescInfo in businessProcDescInfoList in updateEmployeeWithBizDescData() UPDATED");
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateEmployeeWithBizDescData(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //**********************************************************************************************************************
    //*******************************SECURITY_SERVICE_API_PROXING***********************************************************
    //**********************************************************************************************************************
    //"http:/localhost:8194/gateway_API/security_service/create-user" (:)
    @PostMapping(SCRT_SERVICE_URI_COMMON_DIR_STRING + CREATE_USER_POST_URI_STRING)
    public ResponseEntity<String> createUser(HttpServletRequest request, @RequestBody UserInfo userInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createUser() - START");
        return this.proxingExternalRequests(userInfo, HttpMethod.POST, request,
                CREATE_USER_POST_URI_TMPLT);
    }

    /*
    //"http:/localhost:8194/gateway_API/security_service/read-{id}" (:)
    @GetMapping(SCRT_SERVICE_URI_COMMON_DIR_STRING + READ_BY_ID_USER_GET_URI_STRING + "{id}")
    public ResponseEntity<String> findUserById(HttpServletRequest request, @PathVariable Integer id)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findUserById() - START" + "\n" + "id param: " + String.valueOf(id));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_ID_USER_GET_URI_TMPLT + String.valueOf(id));
    }
    //*/

    //"http:/localhost:8194/gateway_API/security_service/read-by-usr-uuid-{userUuid}" (:)
    @GetMapping(SCRT_SERVICE_URI_COMMON_DIR_STRING + READ_BY_USR_UUID_USER_GET_URI_STRING + "{userUuid}")
    public ResponseEntity<String> findUsersByUserUuid(HttpServletRequest request, @PathVariable String userUuid)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findUsersByUserUuid() - START" + "\n" + "id param: " + userUuid);
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_USR_UUID_USER_GET_URI_TMPLT + userUuid);
    }

    //"http:/localhost:8194/gateway_API/security_service/read-by-usr-login-{userLogin}-email-{userEmail}" (:)
    @GetMapping(SCRT_SERVICE_URI_COMMON_DIR_STRING + READ_BY_LGN_EML_USER_GET_URI_STRING + "{userLogin}" + "-email-" + "{userEmail}")
    public ResponseEntity<String> findUserByLoginEmail(HttpServletRequest request, @PathVariable String userLogin, @PathVariable String userEmail)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findUserByLoginEmail() - START" + "\n" + " userLogin: " + userLogin + " userEmail: " + userEmail);
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_BY_LGN_EML_USER_GET_URI_TMPLT + userLogin + "-email-" + userEmail);
    }

    //"http:/localhost:8194/gateway_API/security_service/read-all" (:)
    @GetMapping(SCRT_SERVICE_URI_COMMON_DIR_STRING + READ_ALL_USER_GET_URI_STRING)
    public ResponseEntity<String> findAllUsers(HttpServletRequest request)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllUsers() - START");
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_USER_GET_URI_TMPLT);
    }

    //"http:/localhost:8194/gateway_API/security_service/read-all-paginated" (:)
    @RequestMapping(value = SCRT_SERVICE_URI_COMMON_DIR_STRING + READ_ALL_PGNTD_USER_GET_URI_STRING, params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<String> findAllUserPaginated(HttpServletRequest request,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "sizeLimit", defaultValue = "100") int sizeLimit)
            throws URISyntaxException {
        logger.info("API_Gateway_controller findAllUserPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                "sizeLimit param: " + String.valueOf(sizeLimit));
        return this.proxingExternalRequests(null, HttpMethod.GET, request,
                READ_ALL_PGNTD_USER_GET_URI_TMPLT + "?" + "page=" + String.valueOf(page) + "&" +
                        "sizeLimit=" + String.valueOf(sizeLimit));
    }

    //"http:/localhost:8194/gateway_API/security_service/update-user" (:)
    @PutMapping(SCRT_SERVICE_URI_COMMON_DIR_STRING + UPDATE_BY_ID_USER_PUT_URI_STRING)
    public ResponseEntity<String> updateUser(HttpServletRequest request, @RequestBody UserInfo userInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller updateUser() - START" + "\n" + "id param: " + String.valueOf(userInfo.getUserId()));
        return this.proxingExternalRequests(userInfo, HttpMethod.PUT, request,
                UPDATE_BY_ID_USER_PUT_URI_TMPLT);
    }

    //"http:/localhost:8194/gateway_API/security_service/delete-{userId}" (:)
    @DeleteMapping(SCRT_SERVICE_URI_COMMON_DIR_STRING + DELETE_USER_DELETE_URI_STRING + "{userId}")
    public ResponseEntity<String> deleteUserById(HttpServletRequest request, @PathVariable Integer userId)
            throws URISyntaxException {
        logger.info("API_Gateway_controller deleteUserById() - START" + "\n" + "id param: " + String.valueOf(userId));
        return this.proxingExternalRequests(null, HttpMethod.DELETE, request,
                DELETE_USER_DELETE_URI_TMPLT + String.valueOf(userId));
    }

    //[https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable] [:]
    //"http:/localhost:8194/gateway_API/security_service/authorization/create-auth-code" (:)
    //@PostMapping(SCRT_SERVICE_URI_COMMON_DIR_STRING + "/authorization" + "/create-auth-code")
    @RequestMapping(value = {SCRT_SERVICE_AUTH_URI_COMMON_DIR_STRING + CREATE_AUTH_CODE_POST_URI_STRING}, method = RequestMethod.POST)
    @ResponseBody
    public String createAuthCode(HttpServletRequest request, @RequestBody AuthorizationCodeInfo authorizationCodeInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createAuthCode() - START");
        String authCodeAsString = this.proxingExternalRequests(authorizationCodeInfo, HttpMethod.POST, request,
                CREATE_AUTH_CODE_POST_URI_TMPLT).getBody();
        return authCodeAsString;
    }

    @RequestMapping(value = {SCRT_SERVICE_AUTH_URI_COMMON_DIR_STRING + CREATE_ACCESS_TOKEN_POST_URI_STRING}, method = RequestMethod.POST)
    @ResponseBody
    public String createAccessToken(HttpServletRequest request, @RequestBody TokenInfo tokenInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createAccessToken() - START");
        String tokenAsString = this.proxingExternalRequests(tokenInfo, HttpMethod.POST, request,
                CREATE_ACCESS_TOKEN_POST_URI_TMPLT).getBody();
        return tokenAsString;
    }
}

