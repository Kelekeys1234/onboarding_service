//package com.calvary.onboarding.utils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//public class HashingAlgorithmUtils {
//	@Autowired
//	private HashingAlgorithmDao hashingAlgorithmDao;
//
//	public HashingAlgorithm saveHashingAlgorithm(String algorithmName) {
//		HashingAlgorithm existingAlgorithm = hashingAlgorithmDao.getByName(algorithmName).orElse(null);
//		if (ObjectUtils.isEmpty(existingAlgorithm)) {
//			log.info("hashing algorithm not found.");
//			throw new BadRequestException("hashing algorithm not found.");
//		}
//		return existingAlgorithm;
//
//	}
//
//}
