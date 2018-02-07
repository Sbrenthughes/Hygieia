package com.capitalone.dashboard.rest;

        import com.capitalone.dashboard.misc.HygieiaException;
        import com.capitalone.dashboard.model.Collector;
        import com.capitalone.dashboard.request.CollectorRequest;
        import com.capitalone.dashboard.service.CollectorService;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.bind.annotation.RequestMapping;

        import javax.validation.Valid;
        import java.util.ArrayList;
        import java.util.Collection;

        import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
        import static org.springframework.web.bind.annotation.RequestMethod.GET;
        import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PropertyManagerController {
    private final CollectorService collectorService;
    @Autowired
    public PropertyManagerController(CollectorService collectorService)  {
        this.collectorService = collectorService;
    }
    @RequestMapping(value = "/propertyManager/propertyList/", method = GET,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Collector>> getPropertyList() {
        Collection<String> excludeTypeList = new ArrayList<>();
        Collection<String> excludeNameList = new ArrayList<>();

        excludeTypeList.add("Product");
        excludeNameList.add("Product");

        Iterable<Collector> pageOfPropertyItems = collectorService.collectorsExcludedByTypeAndName(excludeTypeList, excludeNameList);

        return ResponseEntity
                .ok()
                .body(pageOfPropertyItems);

    }
    @RequestMapping(value = "/propertyManager/getSelectedProperty/", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collector> getSelectedProperty( @Valid @RequestBody CollectorRequest collectorRequest) throws HygieiaException{
        Collector collector = collectorRequest.toCollector();
        return ResponseEntity
                .ok().body( collectorService.getCollectorByTypeAndName(collector.getName(),collector.getCollectorType()));
    }
    @RequestMapping(value = "/propertyManager/updateProperties/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collector> updateProperties( @Valid @RequestBody CollectorRequest collectorRequest) throws HygieiaException{

        try {
            Collector collector = collectorRequest.toCollector();

            return ResponseEntity
                    .ok().body(collectorService.updatePropertyItem(collector));
        } catch (HygieiaException he) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

    }
    @RequestMapping(value = "/propertyManager/removePropertyItem/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collector> removePropertyItem( @Valid @RequestBody CollectorRequest collectorRequest) throws HygieiaException{

        try {

            return ResponseEntity
                    .ok().body(collectorService.removePropertyItem(collectorRequest));
        } catch (HygieiaException he) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

    }
}