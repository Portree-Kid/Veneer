package textures.texture;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.python.core.PyArray;
import org.python.core.PyBoolean;
import org.python.core.PyFloat;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

/**
 * 
 * Object representing a python texture. Must reside in the package referenced
 * by
 * 
 */

public class Texture extends PyObject {

	static {
		System.out.println("Loaded Texture");
	}

	private PyObject h_size_meters = null;
	private PyObject v_size_meters = null;
	private PyObject height_max = null;
	private PyObject height_min = null;
	private PyObject levels = null;

	private PyList h_cuts = null;
	private PyList v_cuts = null;
	private PyBoolean h_can_repeat = null;
	private PyBoolean v_can_repeat = null;
	private PyBoolean v_align_bottom = null;
	private PyList provides = null;
	private PyList requires = null;
	private PyString name;

	public Texture() {
	}

	public Texture(PyObject[] args, String[] keywords) {

		int offset = args.length - keywords.length;
		System.out.println(offset);
		if (offset > 0) {
			name = (PyString) args[0];
		}
		for (int i = 0; i < keywords.length; i++) {
			String paramName = keywords[i];
			PyObject value = args[i + offset];

			System.out.println(paramName + "\t" + value.getClass().getSimpleName() + "\t" + value);

			try {
				Field f = getClass().getDeclaredField(paramName);
				f.setAccessible(true);
				f.set(this, value);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(args);
	}

	/**
	 * @return the h_size_meters
	 */
	public PyObject getH_size_meters() {
		return h_size_meters;
	}

	/**
	 * @param h_size_meters
	 *            the h_size_meters to set
	 */
	public void setH_size_meters(PyObject h_size_meters) {
		this.h_size_meters = h_size_meters;
	}

	/**
	 * @return the v_size_meters
	 */
	public PyObject getV_size_meters() {
		return v_size_meters;
	}

	/**
	 * @param v_size_meters
	 *            the v_size_meters to set
	 */
	public void setV_size_meters(PyObject v_size_meters) {
		this.v_size_meters = v_size_meters;
	}

	/**
	 * @return the height_max
	 */
	public PyObject getHeight_max() {
		return height_max;
	}

	/**
	 * @param height_max
	 *            the height_max to set
	 */
	public void setHeight_max(PyObject height_max) {
		this.height_max = height_max;
	}

	/**
	 * @return the height_min
	 */
	public PyObject getHeight_min() {
		return height_min;
	}

	/**
	 * @param height_min
	 *            the height_min to set
	 */
	public void setHeight_min(PyObject height_min) {
		this.height_min = height_min;
	}

	/**
	 * @return the levels
	 */
	public PyObject getLevels() {
		return levels;
	}

	/**
	 * @param levels
	 *            the levels to set
	 */
	public void setLevels(PyObject levels) {
		this.levels = levels;
	}

	/**
	 * @return the h_cuts
	 */
	public PyList getH_cuts() {
		return h_cuts;
	}

	/**
	 * @param h_cuts
	 *            the h_cuts to set
	 */
	public void setH_cuts(PyList h_cuts) {
		this.h_cuts = h_cuts;
	}

	/**
	 * @return the v_cuts
	 */
	public PyList getV_cuts() {
		return v_cuts;
	}

	/**
	 * @param v_cuts
	 *            the v_cuts to set
	 */
	public void setV_cuts(PyList v_cuts) {
		this.v_cuts = v_cuts;
	}

	/**
	 * @return the h_can_repeat
	 */
	public PyBoolean getH_can_repeat() {
		return h_can_repeat;
	}

	/**
	 * @param h_can_repeat
	 *            the h_can_repeat to set
	 */
	public void setH_can_repeat(PyBoolean h_can_repeat) {
		this.h_can_repeat = h_can_repeat;
	}

	/**
	 * @return the v_can_repeat
	 */
	public PyBoolean getV_can_repeat() {
		return v_can_repeat;
	}

	/**
	 * @param v_can_repeat
	 *            the v_can_repeat to set
	 */
	public void setV_can_repeat(PyBoolean v_can_repeat) {
		this.v_can_repeat = v_can_repeat;
	}

	/**
	 * @return the v_align_bottom
	 */
	public PyBoolean getV_align_bottom() {
		return v_align_bottom;
	}

	/**
	 * @param v_align_bottom
	 *            the v_align_bottom to set
	 */
	public void setV_align_bottom(PyBoolean v_align_bottom) {
		this.v_align_bottom = v_align_bottom;
	}

	/**
	 * @return the provides
	 */
	public PyList getProvides() {
		return provides;
	}

	/**
	 * @param provides
	 *            the provides to set
	 */
	public void setProvides(PyList provides) {
		this.provides = provides;
	}

	/**
	 * @return the requires
	 */
	public PyList getRequires() {
		return requires;
	}

	/**
	 * @param requires
	 *            the requires to set
	 */
	public void setRequires(PyList requires) {
		this.requires = requires;
	}

	/**
	 * @return the name
	 */
	public PyString getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(PyString name) {
		this.name = name;
	}

	@Override
	public String toString() {
		String fName = name.toString();
		if (fName.lastIndexOf("/") >= 0)
			fName = fName.substring(fName.lastIndexOf("/"), fName.length());
		return fName;
	}
}
