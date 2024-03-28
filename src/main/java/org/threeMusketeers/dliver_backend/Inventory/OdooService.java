package org.threeMusketeers.dliver_backend.Inventory;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@Service
public class OdooService {
    final String url = "https://threemusketeers.odoo.com",
    db = "threemusketeers",
    username = "3emusketeers@gmail.com",
    password = "a5482f84656a9526d5acd8c59ff07548563bd673";

    int uid = -1;



    public Integer authenticate() throws XmlRpcException, MalformedURLException {
        final XmlRpcClient client = new XmlRpcClient();

//        final XmlRpcClientConfigImpl start_config = new XmlRpcClientConfigImpl();
        // testing code
//        start_config.setServerURL(new URL("https://demo.odoo.com/start"));
//        final Map<String, String> info = (Map<String, String>)client.execute(start_config, "start", emptyList());
//
//        final String url = info.get("host"),
//                db = info.get("database"),
//                username = info.get("user"),
//                password = info.get("password");

        final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
        common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        client.execute(common_config, "version", emptyList());
        return (int)client.execute(common_config, "authenticate", asList(db, username, password, emptyMap()));
    }

    /**
     * @return list of id for the given search
     * @throws MalformedURLException
     * @throws XmlRpcException
     */
    public List<Object> listRecords() throws MalformedURLException, XmlRpcException {
        if(uid == -1){
            uid = authenticate();
        }
        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            }});
        }};

        return asList((Object[])models.execute("execute_kw",
                asList(db, uid, password,
                        "stock.picking",
                        "search",
                        List.of(List.of(asList("partner_id", "=", "ashirkul")
                        )))
        ));
    }

    /**
     * @return list of rows for the given search
     * @throws MalformedURLException
     * @throws XmlRpcException
     */
    public Map readRecords() throws MalformedURLException, XmlRpcException {
        if(uid == -1){
            uid = authenticate();
        }
        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            }});
        }};

        List<Object> ids = listRecords();
        // count the number of fields fetched by default
        Map map = (Map<?,?>) ((Object[])models.execute(
                "execute_kw", asList(
                        db, uid, password,
                        "stock.picking",
                        "read",
                        List.of(ids)
                )
        ))[0];
        return map;
    }

    /**
     * @return list of rows for the given search
     * @throws MalformedURLException
     * @throws XmlRpcException
     */
    public Map<?,?> inspectTable() throws MalformedURLException, XmlRpcException, NullPointerException {
        if(uid == -1){
            uid = authenticate();
        }
        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            }});
        }};
        /* count the number of fields fetched by default */
        Map<?,?> map = (Map<?,?>)models.execute("execute_kw", List.of(
                db, uid, password,
                "stock.picking", "fields_get",
                emptyList(),
                new HashMap<>() {{put("attributes", asList("string", "help", "type"));}}
        ));
        return map;
    }

    /**
     * @return list of rows for the given search
     * @throws MalformedURLException
     * @throws XmlRpcException
     */
    public List<?> searchRead() throws MalformedURLException, XmlRpcException, NullPointerException {
        if(uid == -1){
            uid = authenticate();
        }
        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            }});
        }};
        /* count the number of fields fetched by default */
        List<?> list = asList((Object[])models.execute("execute_kw", asList(
                db, uid, password,
                "stock.picking", "search_read",
                List.of(List.of(asList("partner_id", "=", "ashirkul"))),
                new HashMap<>() {{put("fields", asList("name", "partner_id", "state"));put("limit", 5);
                }}
        )));
        return list;
    }

    /**
     * @return list of rows for the given search
     * @throws MalformedURLException
     * @throws XmlRpcException
     */
    public Integer createRecords() throws MalformedURLException, XmlRpcException, NullPointerException {
        if(uid == -1){
            uid = authenticate();
        }
        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            }});
        }};
        /* count the number of fields fetched by default */
        final Integer id = (Integer)models.execute("execute_kw", asList(
                db, uid, password,
                "stock.picking", "create",
                List.of(new HashMap<>() {{
                    put("name", "New Partner");
                    put("partner_id", 7);
                    put("picking_type_id", 2);
                }})
        ));
        return id;
    }

    /**
     * @return list of rows for the given search
     * @throws MalformedURLException
     * @throws XmlRpcException
     */
    public Boolean updateRecord() throws MalformedURLException, XmlRpcException, NullPointerException {
        if(uid == -1){
            uid = authenticate();
        }
        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            }});
        }};
        /* count the number of fields fetched by default */
        models.execute("execute_kw", asList(
                db, uid, password,
                "stock.picking", "write",
                asList(List.of(3), new HashMap<>() {{
                    put("display_name", "Newer Name");
                    put("name", "Newer Name");
                }})
        ));
        return true;
    }

    /**
     * @return list of rows for the given search
     * @throws MalformedURLException
     * @throws XmlRpcException
     */
    public Boolean deleteRecord() throws MalformedURLException, XmlRpcException, NullPointerException {
        if(uid == -1){
            uid = authenticate();
        }
        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            }});
        }};
        /* count the number of fields fetched by default */
        models.execute("execute_kw", asList(
                db, uid, password,
                "stock.picking", "unlink",
                List.of(List.of(3))));

        List exists = asList((Object[])models.execute("execute_kw", asList(
                db, uid, password,
                "stock.picking", "search",
                List.of(List.of(asList("id", "=", 78)))
        )));
        return exists.isEmpty();
    }

}
