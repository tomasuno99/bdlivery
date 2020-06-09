package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;

public class DBliveryStatisticsServiceImpl implements DBliveryStatisticsService {

	public DBliveryStatisticsServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Order> getAllOrdersMadeByUser(String username) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> getTopNSuppliersInSentOrders(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getPendingOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getSentOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getDeliveredOrdersForUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getBestSellingProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsOnePrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getSoldProductsOn(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getMaxWeigth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getOrderNearPlazaMoreno() {
		// TODO Auto-generated method stub
		return null;
	}

}
