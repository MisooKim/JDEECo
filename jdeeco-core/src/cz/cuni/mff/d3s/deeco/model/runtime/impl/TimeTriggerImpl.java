/**
 */
package cz.cuni.mff.d3s.deeco.model.runtime.impl;

import cz.cuni.mff.d3s.deeco.model.runtime.api.TimeTrigger;

import cz.cuni.mff.d3s.deeco.model.runtime.meta.RuntimeMetadataPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Time Trigger</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.TimeTriggerImpl#getPeriod <em>Period</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.TimeTriggerImpl#getOffset <em>Offset</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.TimeTriggerImpl#getOrder <em>Order</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.TimeTriggerImpl#getWcet <em>Wcet</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TimeTriggerImpl extends TriggerImpl implements TimeTrigger {
	/**
	 * The default value of the '{@link #getPeriod() <em>Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPeriod()
	 * @generated
	 * @ordered
	 */
	protected static final long PERIOD_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getPeriod() <em>Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPeriod()
	 * @generated
	 * @ordered
	 */
	protected long period = PERIOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected static final long OFFSET_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected long offset = OFFSET_EDEFAULT;

	/**
	 * The default value of the '{@link #getOrder() <em>Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrder()
	 * @generated
	 * @ordered
	 */
	protected static final int ORDER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOrder() <em>Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrder()
	 * @generated
	 * @ordered
	 */
	protected int order = ORDER_EDEFAULT;

	/**
	 * The default value of the '{@link #getWcet() <em>Wcet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWcet()
	 * @generated
	 * @ordered
	 */
	protected static final long WCET_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getWcet() <em>Wcet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWcet()
	 * @generated
	 * @ordered
	 */
	protected long wcet = WCET_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TimeTriggerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuntimeMetadataPackage.Literals.TIME_TRIGGER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getPeriod() {
		return period;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPeriod(long newPeriod) {
		long oldPeriod = period;
		period = newPeriod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.TIME_TRIGGER__PERIOD, oldPeriod, period));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getOffset() {
		return offset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOffset(long newOffset) {
		long oldOffset = offset;
		offset = newOffset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.TIME_TRIGGER__OFFSET, oldOffset, offset));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrder(int newOrder) {
		int oldOrder = order;
		order = newOrder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.TIME_TRIGGER__ORDER, oldOrder, order));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getWcet() {
		return wcet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWcet(long newWcet) {
		long oldWcet = wcet;
		wcet = newWcet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.TIME_TRIGGER__WCET, oldWcet, wcet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RuntimeMetadataPackage.TIME_TRIGGER__PERIOD:
				return getPeriod();
			case RuntimeMetadataPackage.TIME_TRIGGER__OFFSET:
				return getOffset();
			case RuntimeMetadataPackage.TIME_TRIGGER__ORDER:
				return getOrder();
			case RuntimeMetadataPackage.TIME_TRIGGER__WCET:
				return getWcet();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RuntimeMetadataPackage.TIME_TRIGGER__PERIOD:
				setPeriod((Long)newValue);
				return;
			case RuntimeMetadataPackage.TIME_TRIGGER__OFFSET:
				setOffset((Long)newValue);
				return;
			case RuntimeMetadataPackage.TIME_TRIGGER__ORDER:
				setOrder((Integer)newValue);
				return;
			case RuntimeMetadataPackage.TIME_TRIGGER__WCET:
				setWcet((Long)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RuntimeMetadataPackage.TIME_TRIGGER__PERIOD:
				setPeriod(PERIOD_EDEFAULT);
				return;
			case RuntimeMetadataPackage.TIME_TRIGGER__OFFSET:
				setOffset(OFFSET_EDEFAULT);
				return;
			case RuntimeMetadataPackage.TIME_TRIGGER__ORDER:
				setOrder(ORDER_EDEFAULT);
				return;
			case RuntimeMetadataPackage.TIME_TRIGGER__WCET:
				setWcet(WCET_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RuntimeMetadataPackage.TIME_TRIGGER__PERIOD:
				return period != PERIOD_EDEFAULT;
			case RuntimeMetadataPackage.TIME_TRIGGER__OFFSET:
				return offset != OFFSET_EDEFAULT;
			case RuntimeMetadataPackage.TIME_TRIGGER__ORDER:
				return order != ORDER_EDEFAULT;
			case RuntimeMetadataPackage.TIME_TRIGGER__WCET:
				return wcet != WCET_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (period: ");
		result.append(period);
		result.append(", offset: ");
		result.append(offset);
		result.append(", order: ");
		result.append(order);
		result.append(", wcet: ");
		result.append(wcet);
		result.append(')');
		return result.toString();
	}

} //TimeTriggerImpl
