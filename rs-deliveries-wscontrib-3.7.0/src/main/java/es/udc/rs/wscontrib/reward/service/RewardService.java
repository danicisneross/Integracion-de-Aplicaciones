package es.udc.rs.wscontrib.reward.service;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(name = "RewardProvider", serviceName = "RewardProviderService", targetNamespace = "http://rs.udc.es/reward")
public class RewardService {

	private static Map<String, Boolean> customers;
	private static Map<Long, Integer> productRewards;

	private static Map<String, Long> customerRewards;

	public RewardService() {
	}

	@PostConstruct()
	private void init() {
		customers = new HashMap<String, Boolean>();
		customers.put("11838555H", Boolean.TRUE);
		customers.put("41262338Q", Boolean.TRUE);
		customers.put("77201494Q", Boolean.TRUE);
		customers.put("08724415D", Boolean.FALSE);
		customers.put("81191750W", Boolean.FALSE);

		productRewards = new HashMap<Long, Integer>();
		productRewards.put(1L, 10);
		productRewards.put(2L, 20);
		productRewards.put(3L, 10);
		productRewards.put(4L, 40);
		productRewards.put(5L, 100);

		customerRewards = new HashMap<String, Long>();
		customerRewards.put("11838555H", 0L);
		customerRewards.put("41262338Q", 0L);
		customerRewards.put("77201494Q", 0L);
	}

	private boolean validateRewardMembership(String customerId)
			throws RewardException {
		Boolean isCustomer = customers.get(customerId);
		if (isCustomer == null) {
			throw new RewardException(new RewardExceptionInfo("Customer '"
					+ customerId + "' not found"));
		}
		return isCustomer;
	}

	@WebMethod(operationName = "isRewardMembership")
	public boolean isRewardMembership(
			@WebParam(name = "customerId") String customerId)
			throws RewardException {
		return validateRewardMembership(customerId);
	}

	@WebMethod(operationName = "getProductReward")
	public Integer getProductReward(@WebParam(name = "productId") Long productId)
			throws RewardException {
		Integer rewardPoints = productRewards.get(productId);
		if (rewardPoints == null) {
			throw new RewardException(new RewardExceptionInfo("Product '"
					+ productId + "' not found"));
		}
		return rewardPoints;
	}

	@WebMethod(operationName = "addMembershipReward")
	public Long addMembershipReward(
			@WebParam(name = "customerId") String customerId,
			@WebParam(name = "rewardPoints") Long rewardPoints)
			throws RewardException {
		Boolean isCustomer = validateRewardMembership(customerId);
		if (!isCustomer) {
			throw new RewardException(new RewardExceptionInfo("Customer '"
					+ customerId + "' is not subscribed to the Reward Service"));
		}
		Long totalRewardPoints = customerRewards.get(customerId);
		if (totalRewardPoints == null) {
			totalRewardPoints = 0L;
		}
		if (rewardPoints == null || rewardPoints <= 0) {
			throw new RewardException(new RewardExceptionInfo(
					"Invalid reward '" + rewardPoints + "'"));
		}
		totalRewardPoints += rewardPoints;
		customerRewards.put(customerId, totalRewardPoints);
		return totalRewardPoints;
	}

	@WebMethod(operationName = "removeMembershipReward")
	public Long removeMembershipReward(
			@WebParam(name = "customerId") String customerId,
			@WebParam(name = "rewardPoints") Long rewardPoints)
			throws RewardException {
		Boolean isCustomer = validateRewardMembership(customerId);
		if (!isCustomer) {
			throw new RewardException(new RewardExceptionInfo("Customer '"
					+ customerId + "' is not subscribed to the Reward Service"));
		}
		Long totalRewardPoints = customerRewards.get(customerId);
		if (totalRewardPoints == null) {
			totalRewardPoints = 0L;
		}
		if (rewardPoints == null || rewardPoints <= 0
				|| rewardPoints > totalRewardPoints) {
			throw new RewardException(new RewardExceptionInfo(
					"Invalid reward '" + rewardPoints + "'"));
		}
		totalRewardPoints -= rewardPoints;
		customerRewards.put(customerId, totalRewardPoints);
		return totalRewardPoints;
	}

	@WebMethod(operationName = "getMembershipReward")
	public Long getMembershipReward(
			@WebParam(name = "customerId") String customerId)
			throws RewardException {
		Boolean isCustomer = validateRewardMembership(customerId);
		if (!isCustomer) {
			throw new RewardException(new RewardExceptionInfo("Customer '"
					+ customerId + "' is not subscribed to the Reward Service"));
		}
		Long totalRewardPoints = customerRewards.get(customerId);
		if (totalRewardPoints == null) {
			throw new RewardException(new RewardExceptionInfo("Customer '"
					+ customerId + "' not found"));
		}
		return totalRewardPoints;
	}

}
