package org.pentaho.platform.plugin.adhoc;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.pentaho.platform.api.engine.IPluginManager;
import org.pentaho.platform.api.engine.IPluginResourceLoader;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.engine.services.solution.SimpleContentGenerator;
import org.pentaho.platform.plugin.services.pluginmgr.PluginClassLoader;

public class AdhocEditorContentGenerator extends SimpleContentGenerator {
  private static final long serialVersionUID = 1L;

  @Override
  public Log getLogger() {
    return null; //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void createContent(OutputStream outputStream) throws Exception {
    IPluginResourceLoader pluginResourceLoader = PentahoSystem.get(IPluginResourceLoader.class);
    IPluginManager pluginManager = PentahoSystem.get(IPluginManager.class);
    ClassLoader classLoader = pluginManager.getClassLoader("waqr"); //$NON-NLS-1$
    InputStream inputStream = pluginResourceLoader.getResourceAsStream(classLoader, "/resources/waqr.html"); //$NON-NLS-1$
    int val;
    while ((val = inputStream.read()) != -1) {
      outputStream.write(val);
    }
    outputStream.flush();
  }

  @Override
  public String getMimeType() {
    return "text/html"; //$NON-NLS-1$
  }

  public String getSystemRelativePluginPath(ClassLoader classLoader) {
    File dir = getPluginDir(classLoader);
    if (dir == null) {
      return null;
    }
    // get the full path with \ converted to /
    String path = dir.getAbsolutePath().replace('\\', '/');
    int pos = path.lastIndexOf("/system/"); //$NON-NLS-1$
    if (pos != -1) {
      path = path.substring(pos + 8);
    }
    return path;
  }

  protected File getPluginDir(ClassLoader classLoader) {
    if (classLoader instanceof PluginClassLoader) {
      return ((PluginClassLoader) classLoader).getPluginDir();
    }
    return null;
  }
}
