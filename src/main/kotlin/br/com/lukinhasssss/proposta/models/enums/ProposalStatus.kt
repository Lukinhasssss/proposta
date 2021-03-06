package br.com.lukinhasssss.proposta.models.enums

enum class ProposalStatus {
    NOT_ELIGIBLE, ELIGIBLE;

    companion object {
        fun convert(solicitationResult: String): ProposalStatus? {
            val mapper: HashMap<String, ProposalStatus> = hashMapOf()

            mapper["COM_RESTRICAO"] = NOT_ELIGIBLE
            mapper["SEM_RESTRICAO"] = ELIGIBLE

            return mapper[solicitationResult]
        }

        fun convertToResponse(proposalStatus: String): String {
            val mapper: HashMap<String, String> = hashMapOf()

            mapper[NOT_ELIGIBLE.name] = "Ineligible proposal"
            mapper[ELIGIBLE.name] = "Eligible proposal"

            return mapper[proposalStatus]!!
        }
    }
}
