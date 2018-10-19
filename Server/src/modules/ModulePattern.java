package modules;

import msc.Client;
import msc.Strings;

import static msc.Strings.log_module_err;


// Used to create lists of modules.
public class ModulePattern {
    Client client;

    public String exec(String cmd) {
        return log_module_err;
    }

    public void logSucces(String log) {
        client.getWriter().print(Strings.log_success + log);
    }

    public void logError(String log) {
        client.getWriter().print(Strings.log_error + log);
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public Client getClient() {
        return client;
    }
}
