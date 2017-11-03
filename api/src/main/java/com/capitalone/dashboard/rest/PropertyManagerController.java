package com.capitalone.dashboard.rest;

        import com.capitalone.dashboard.misc.HygieiaException;
        import com.capitalone.dashboard.model.PropertyManager;
        import com.capitalone.dashboard.request.PropertyManagerRequest;
        import com.capitalone.dashboard.service.PropertyManagerService;
        import com.capitalone.dashboard.util.PaginationHeaderUtility;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.web.PageableDefault;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;
        import org.springframework.web.multipart.MultipartHttpServletRequest;


        import javax.validation.Valid;
        import java.io.IOException;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Properties;

        import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
        import static org.springframework.web.bind.annotation.RequestMethod.GET;
        import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PropertyManagerController {

    private final PropertyManagerService propertyManagerService;
    private PaginationHeaderUtility paginationHeaderUtility;

    @Autowired
    public PropertyManagerController(PropertyManagerService propertyManagerService,
                                         PaginationHeaderUtility paginationHeaderUtility) {
        this.propertyManagerService = propertyManagerService;
        this.paginationHeaderUtility = paginationHeaderUtility;
    }
    @RequestMapping(value = "/propertyManager/propertiesFileUpload/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fileUpload(MultipartHttpServletRequest request) {

        try {
            Iterator<String> itr=request.getFileNames();
            MultipartFile file = request.getFile(itr.next());



            Properties collectorProperties = new Properties();
            PropertyManager properties = new PropertyManager();
            collectorProperties.load(file.getInputStream());
            properties.setProperties(collectorProperties);
            propertyManagerService.create(properties);

            return ResponseEntity.ok("created");

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        } catch (HygieiaException he) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(he.getMessage());
        }

    }
    @RequestMapping(value = "/propertyManager/propertyList/", method = GET,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PropertyManager>> getPropertyList(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {

        Iterable<PropertyManager> pageOfConfigurationItems = propertyManagerService.collectorPropertiesWithFilter(pageable);

        return ResponseEntity
                .ok()
                .body(pageOfConfigurationItems);

    }
    @RequestMapping(value = "/propertyManager/getSelectedProperty/{type}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyManager> getSelectedProperty(@PathVariable String type) {
        try {
            PropertyManager properties = propertyManagerService.getByName(type);
            return ResponseEntity
                    .ok().body(properties);
        } catch (HygieiaException he) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }
    @RequestMapping(value = "/propertyManager/updateProperties/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyManager> updateProperties( @Valid @RequestBody PropertyManagerRequest collectorPropertiesRequest) throws HygieiaException{

        try {
            PropertyManager properties = collectorPropertiesRequest.toCollectorProperties();
            return ResponseEntity
                    .ok().body(propertyManagerService.update(properties));
        } catch (HygieiaException he) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

    }
    @RequestMapping(value = "/propertyManager/removeProperties/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyManager> removeProperties( @Valid @RequestBody PropertyManagerRequest collectorPropertiesRequest) throws HygieiaException{

        try {
            return ResponseEntity
                    .ok().body(propertyManagerService.remove(collectorPropertiesRequest));
        } catch (HygieiaException he) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

    }
}